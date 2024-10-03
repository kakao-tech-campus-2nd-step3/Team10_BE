package poomasi.domain.review.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("별점")
    private float rating;

    @Comment("리뷰 내용")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductReviewPhoto> imageUrl = new ArrayList<>();

//    @Comment("작성자")
//    @ManyToOne
//    private Member reviewer;

    public ProductReview(float rating, String content, Product product) {
        this.rating = rating;
        this.content = content;
        this.product = product;
    }
}
