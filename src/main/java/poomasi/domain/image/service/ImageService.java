package poomasi.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.image.deleteLinker.ImageDeleteFactory;
import poomasi.domain.image.deleteLinker.ImageDeleteLinker;
import poomasi.domain.image.dto.ImageRequest;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;
import poomasi.domain.image.linker.ImageLinker;
import poomasi.domain.image.linker.ImageLinkerFactory;
import poomasi.domain.image.repository.ImageRepository;
import poomasi.domain.image.validator.ImageOwnerValidator;
import poomasi.domain.image.validator.ImageOwnerValidatorFactory;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.service.MemberService;
import poomasi.global.error.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private static final int DEFAULT_IMAGE_LIMIT = 5;
    private static final int IMAGE_ONE_LIMIT = 1;

    private final ImageRepository imageRepository;
    private final MemberService memberService;
    private final ImageOwnerValidatorFactory validatorFactory;
    private final ImageLinkerFactory imageLinkerFactory;
    private final ImageDeleteFactory imageDeleteFactory;

    // 이미지 타입에 맞게 link, deleteLink, 개수 제한, ownerValidate

    @Transactional
    public Image saveImage(Long memberId, ImageRequest imageRequest) {
        // 기존 이미지가 있는 경우 복구 또는 예외 처리 (실제 복구 로직과는 차이가 있음)
        validateImageOwner(memberId, imageRequest.type(), imageRequest.referenceId());
        validateImageLimit(imageRequest);

        Image image = findExistingOrRecoverableImage(imageRequest)
                .map(existingImage -> recoverImageOrThrow(existingImage, imageRequest))
                .orElseGet(() -> imageRequest.toEntity(imageRequest));

        imageLink(image);

        return imageRepository.save(image);
    }

    // 이미지 주인이 맞는지 검증
    private void validateImageOwner(Long memberId, ImageType type, Long referenceId) {
        // 관리자는 제외
        if (isAdmin(memberId)) {
            return;
        }
        ImageOwnerValidator validator = validatorFactory.getValidator(type);
        if (validator != null && !validator.validateOwner(memberId, referenceId)) {
            throw new BusinessException(IMAGE_OWNER_MISMATCH);
        }
    }

    private boolean isAdmin(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        return member.isAdmin();
    }

    private Optional<Image> findExistingOrRecoverableImage(ImageRequest imageRequest) {
        return imageRepository.findByObjectKeyAndTypeAndReferenceId(
                imageRequest.objectKey(), imageRequest.type(), imageRequest.referenceId());
    }

    private Image recoverImageOrThrow(Image existingImage, ImageRequest imageRequest) {
        if (existingImage.getDeletedAt() == null) {
            throw new BusinessException(IMAGE_ALREADY_EXISTS);
        }
        existingImage.setDeletedAt(null);
        existingImage.setCreatedAt(LocalDateTime.now());
        existingImage.update(imageRequest);
        return existingImage;
    }

    private void validateImageLimit(ImageRequest imageRequest) {
        int imageLimit = DEFAULT_IMAGE_LIMIT;
        if (imageRequest.type() == ImageType.MEMBER_PROFILE || imageRequest.type() == ImageType.PRODUCT) {
            imageLimit = IMAGE_ONE_LIMIT; // 멤버 프로필, 상품 이미지는 한 장으로 제한
        }

        if (imageRepository.countByTypeAndReferenceIdAndDeletedAtIsNull(imageRequest.type(), imageRequest.referenceId()) >= imageLimit) {
            throw new BusinessException(IMAGE_LIMIT_EXCEED);
        }
    }

    // 여러 이미지 저장
    @Transactional
    public List<Image> saveMultipleImages(Long memberId, List<ImageRequest> imageRequests) {
        return imageRequests.stream()
                .map(imageRequest -> saveImage(memberId, imageRequest))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteImage(Long memberId, Long id) {
        Image image = getImageById(id);
        validateImageOwner(memberId, image.getType(), image.getReferenceId());
        imageRepository.delete(image);

        imageDeleteLink(image);
    }

    public Image getImageById(Long id) {
        return imageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(IMAGE_NOT_FOUND));
    }

    public List<Image> getImagesByTypeAndReferenceId(ImageType type, Long referenceId) {
        return imageRepository.findByTypeAndReferenceIdAndDeletedAtIsNull(type, referenceId);
    }

    // 이미지 수정
    @Transactional
    public Image updateImage(Long memberId, Long id, ImageRequest imageRequest) {
        Image image = getImageById(id);
        validateImageOwner(memberId, image.getType(), image.getReferenceId());

        if (!image.getType().equals(imageRequest.type()) ||
                !image.getReferenceId().equals(imageRequest.referenceId())) {
            validateImageLimit(imageRequest);
        }

        if (!image.getType().equals(imageRequest.type())) {
            imageDeleteLink(image);
        }

        image.update(imageRequest);

        if (!image.getType().equals(imageRequest.type())) {
            imageLink(image);
        }


        return imageRepository.save(image);
    }

    @Transactional
    public void recoverImage(Long memberId, Long id) {
        Image image = getImageById(id);
        validateImageOwner(memberId, image.getType(), image.getReferenceId());

        if (image.getDeletedAt() == null) {
            throw new BusinessException(IMAGE_ALREADY_EXISTS);
        }

        validateImageLimit(image.toRequest(image));

        image.setDeletedAt(null);

        imageLink(image);

        imageRepository.save(image);
    }

    // 이미지와 해당 이미지를 가지는 엔티티 연결
    private void imageLink(Image image){
        ImageLinker linker = imageLinkerFactory.getLinker(image.getType());
        if (linker != null){
            linker.link(image.getReferenceId(), image);
        }
    }

    // 이미지 삭제 시 해당 이미지를 가지는 엔티티에서도 처리
    private void imageDeleteLink(Image image){
        ImageDeleteLinker imageDeleteLinker = imageDeleteFactory.getDeleteLinker(image.getType());
        if (imageDeleteLinker != null) {
            imageDeleteLinker.handleImageDeletion(image);  // 해당 타입에 맞는 삭제 처리
        }
    }


}