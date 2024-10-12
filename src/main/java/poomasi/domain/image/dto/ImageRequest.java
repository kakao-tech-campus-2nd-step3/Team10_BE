package poomasi.domain.image.dto;

import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;

public record ImageRequest(String objectKey, String imageUrl, ImageType type, Long referenceId) {
    public Image toEntity(ImageRequest imageRequest){
        return new Image(
                imageRequest.objectKey,
                imageRequest.imageUrl,
                imageRequest.type,
                imageRequest.referenceId
        );
    }
}

