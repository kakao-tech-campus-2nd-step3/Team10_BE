package poomasi.domain.review.dto;

import java.util.List;
import poomasi.domain.review.entity.Review;

public record ReviewResponse
        (Long id,
         Long productId,
         //Long reviewerId,
         Float rating,
         String content
         //List<String> imageUrls
        ) {

    public static ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getEntityId(),
                //productReview.getReviewer().getId(),
                review.getRating(),
                review.getContent()
        );
    }
}
