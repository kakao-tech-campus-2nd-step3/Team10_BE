package poomasi.domain.image.deleteLinker;

import poomasi.domain.image.entity.Image;

public interface ImageDeleteLinker {
    void handleImageDeletion(Image image);
}