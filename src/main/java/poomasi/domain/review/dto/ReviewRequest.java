package poomasi.domain.review.dto;

import poomasi.domain.member.entity.Member;
import poomasi.domain.review.entity.EntityType;
import poomasi.domain.review.entity.Review;

public record ReviewRequest(
        Float rating,
        String content
) {

    public Review toEntity(Long entityId, EntityType entityType, Member member) {
        return Review.builder()
                .rating(rating)
                .content(content)
                .entityId(entityId)
                .entityType(entityType)
                .reviewer(member)
                .build();
    }
}
