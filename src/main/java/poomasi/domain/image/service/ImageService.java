package poomasi.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.image.dto.ImageRequest;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;
import poomasi.domain.image.repository.ImageRepository;
import poomasi.global.error.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

import static poomasi.global.error.BusinessError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Image saveImage(ImageRequest imageRequest) {
        if (imageRepository.countByTypeAndReferenceId(imageRequest.type(), imageRequest.referenceId()) >= 5) {
            throw new BusinessException(IMAGE_LIMIT_EXCEED);
        }

        if (imageRepository.existsByObjectKeyAndReferenceId(imageRequest.objectKey(), imageRequest.referenceId())) {
            throw new BusinessException(IMAGE_ALREADY_EXISTS);
        }

        Image imageEntity = imageRequest.toEntity(imageRequest);
        return imageRepository.save(imageEntity);
    }

    // 여러 이미지 저장
    @Transactional
    public List<Image> saveMultipleImages(List<ImageRequest> imageRequests) {
        return imageRequests.stream()
                .map(this::saveImage)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new BusinessException(IMAGE_NOT_FOUND));
    }

    public List<Image> getImagesByTypeAndReferenceId(ImageType type, Long referenceId) {
        return imageRepository.findByTypeAndReferenceId(type, referenceId);
    }

    // 이미지 수정
    @Transactional
    public Image updateImage(Long id, ImageRequest imageRequest) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new BusinessException(IMAGE_NOT_FOUND));

        if (imageRepository.countByTypeAndReferenceId(imageRequest.type(), imageRequest.referenceId()) >= 5 &&
                !image.getType().equals(imageRequest.type())) {
            throw new BusinessException(IMAGE_LIMIT_EXCEED);
        }

        image.update(imageRequest);

        return imageRepository.save(image);
    }
}