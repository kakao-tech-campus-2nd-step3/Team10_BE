package poomasi.domain.review.dto;

import poomasi.domain.review.entity.Review;

public record ReviewResponse
        (Long id,
         Long entityId,
         String reviewerName,
         Float rating,
         String content
         //List<String> imageUrls
        ) {

    public static ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getEntityId(),
                review.getReviewer().getName(),
                review.getRating(),
                review.getContent()
        );
    }
}
