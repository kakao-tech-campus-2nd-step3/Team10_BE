package poomasi.domain.image.validator;

import org.springframework.stereotype.Component;
import poomasi.domain.image.entity.ImageType;

import java.util.EnumMap;
import java.util.Map;

@Component
public class ImageOwnerValidatorFactory {
    private final Map<ImageType, ImageOwnerValidator> validators = new EnumMap<>(ImageType.class);

    public ImageOwnerValidatorFactory(FarmOwnerValidator farmOwnerValidator,
                                      ProductOwnerValidator productOwnerValidator,
                                      ReviewOwnerValidator reviewOwnerValidator,
                                      MemberProfileOwnerValidator memberProfileOwnerValidator) {
        validators.put(ImageType.FARM, farmOwnerValidator);
        validators.put(ImageType.PRODUCT, productOwnerValidator);
        validators.put(ImageType.FARM_REVIEW, reviewOwnerValidator);
        validators.put(ImageType.PRODUCT_REVIEW, reviewOwnerValidator);
        validators.put(ImageType.MEMBER_PROFILE, memberProfileOwnerValidator);
    }

    public ImageOwnerValidator getValidator(ImageType type) {
        return validators.get(type);
    }
}
