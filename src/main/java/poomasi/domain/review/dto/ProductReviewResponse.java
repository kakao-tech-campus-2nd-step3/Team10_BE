package poomasi.domain.review.dto;

import java.util.List;
import poomasi.domain.review.entity.ProductReview;
import poomasi.domain.review.entity.ProductReviewPhoto;

public record ProductReviewResponse
        (long id,
         long productId,
         //long reviewerId,
         float rating,
         String content,
         List<String> imageUrls
        ) {

    public static ProductReviewResponse fromEntity(ProductReview productReview) {
        return new ProductReviewResponse(
                productReview.getId(),
                productReview.getProduct().getId(),
                //productReview.getReviewer().getId(),
                productReview.getRating(),
                productReview.getContent(),
                productReview.getImageUrl().stream().map(ProductReviewPhoto::getUrl).toList()
        );
    }
}
