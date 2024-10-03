package poomasi.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poomasi.domain.review.entity.ProductReviewPhoto;

@Repository
public interface ProductReviewPhotoRepository extends JpaRepository<ProductReviewPhoto, Long> {

}
