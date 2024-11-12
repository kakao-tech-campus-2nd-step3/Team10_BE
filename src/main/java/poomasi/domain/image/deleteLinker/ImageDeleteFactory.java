package poomasi.domain.image.deleteLinker;

import org.springframework.stereotype.Component;
import poomasi.domain.image.entity.ImageType;

import java.util.HashMap;
import java.util.Map;

@Component
public class ImageDeleteFactory {

    private final Map<ImageType, ImageDeleteLinker> handlerMap;

    public ImageDeleteFactory(
            ProductDeleteLinker productDeleteLinker,
            MemberProfileDeleteLinker memberProfileDeleteLinker) {
        this.handlerMap = new HashMap<>();
        handlerMap.put(ImageType.PRODUCT, productDeleteLinker);
        handlerMap.put(ImageType.MEMBER_PROFILE, memberProfileDeleteLinker);
    }

    public ImageDeleteLinker getDeleteLinker(ImageType type) {
        return handlerMap.get(type);
    }
}
