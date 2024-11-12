package poomasi.domain.image.linker;

import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;

public interface ImageLinker {
    boolean supports(ImageType type);
    void link(Long referenceId, Image savedImage);
}