package poomasi.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    long countByTypeAndReferenceIdAndDeletedAtIsNull(ImageType type, Long referenceId);
    List<Image> findByTypeAndReferenceIdAndDeletedAtIsNull(ImageType type, Long referenceId);
    Optional<Image> findByIdAndDeletedAtIsNull(Long id);
    Optional<Image> findByObjectKeyAndTypeAndReferenceId(String s, ImageType type, Long aLong);
}