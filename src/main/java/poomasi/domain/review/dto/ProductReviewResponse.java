package poomasi.domain.review.dto;

import poomasi.domain.review.entity.ProductReview;

public record ProductReviewResponse
        (long id ,
         long productId,
         //long reviewerId,
         float rating,
         String content
         )
{
    public static ProductReviewResponse fromEntity(ProductReview productReview) {
        return new ProductReviewResponse(
                productReview.getId(),
                productReview.getProduct().getId(),
                //productReview.getReviewer().getId(),
                productReview.getRating(),
                productReview.getContent()
        );
    }
}
