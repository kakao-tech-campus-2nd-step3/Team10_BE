package poomasi.domain.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poomasi.domain.review.entity.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query("select r from ProductReview r where r.product.id = :productId")
    List<ProductReview> findByProductId(long productId);
}
