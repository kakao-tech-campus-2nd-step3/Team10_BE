package poomasi.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.image.dto.ImageRequest;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.repository.ImageRepository;
import poomasi.global.error.BusinessException;

import java.util.List;

import static poomasi.global.error.BusinessError.IMAGE_ALREADY_EXISTS;
import static poomasi.global.error.BusinessError.IMAGE_LIMIT_EXCEED;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

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

    public List<Image> getImagesByTypeAndReferenceId(ImageRequest imageRequest) {
        return imageRepository.findByTypeAndReferenceId(imageRequest.type(), imageRequest.referenceId());
    }
}