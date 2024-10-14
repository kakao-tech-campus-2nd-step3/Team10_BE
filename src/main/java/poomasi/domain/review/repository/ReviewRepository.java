package poomasi.domain.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poomasi.domain.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review  r where r.entityId = :productId and r.entityType = 'PRODUCT'")
    List<Review> findByProductId(Long productId);

    @Query("select r from Review  r where r.entityId = :farmId and r.entityType = 'FARM'")
    List<Review> findByFarmId(Long farmId);
}
