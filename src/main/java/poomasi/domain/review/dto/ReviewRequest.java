package poomasi.domain.review.dto;

import poomasi.domain.review.entity.Review;

public record ReviewRequest(
        Float rating,
        String content
) {

    public Review toEntity(Long entityId) {
        return new Review(this.rating, this.content, entityId);
    }
}
