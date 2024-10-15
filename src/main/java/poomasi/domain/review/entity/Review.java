package poomasi.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.member.entity.Member;
import poomasi.domain.review.dto.ReviewRequest;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("별점")
    private Float rating;

    @Comment("리뷰 내용")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Comment("엔티티 아이디")
    private Long entityId;

    @Comment("엔티티 타입")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Comment("작성자")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reviewer;

    @Builder
    public Review(Float rating, String content, Long entityId, EntityType entityType,
            Member reviewer) {
        this.rating = rating;
        this.content = content;
        this.entityId = entityId;
        this.entityType = entityType;
        this.reviewer = reviewer;
    }

    public void modifyReview(ReviewRequest reviewRequest) {
        this.rating = reviewRequest.rating();
        this.content = reviewRequest.content();
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
