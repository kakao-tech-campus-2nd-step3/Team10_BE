package poomasi.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor
@Getter
public class ProductReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Comment("리뷰")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductReview productReview;

    @Comment("사진 경로")
    private String url;

    public ProductReviewPhoto(ProductReview productReview, String url) {
        this.productReview = productReview;
        this.url = url;
    }

    public void setReview(ProductReview productReview) {
        this.productReview = productReview;
    }
}
