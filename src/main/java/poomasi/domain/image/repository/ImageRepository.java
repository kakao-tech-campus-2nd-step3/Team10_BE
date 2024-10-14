package poomasi.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    long countByTypeAndReferenceId(ImageType type, Long referenceId);
    boolean existsByObjectKeyAndReferenceId(String objectKey, Long referenceId);
    List<Image> findByTypeAndReferenceId(ImageType type, Long referenceId);
}