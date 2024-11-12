package poomasi.domain.image.linker;

import org.springframework.stereotype.Component;
import poomasi.domain.image.entity.ImageType;

import java.util.List;

@Component
public class ImageLinkerFactory {

    private final List<ImageLinker> linkers;

    public ImageLinkerFactory(List<ImageLinker> linkers) {
        this.linkers = linkers;
    }

    public ImageLinker getLinker(ImageType type) {
        return linkers.stream()
                .filter(linker -> linker.supports(type))
                .findFirst()
                .orElse(null);
    }
}