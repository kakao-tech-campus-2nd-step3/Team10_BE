package poomasi.domain.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.review.entity.Review;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted_at = current_timestamp WHERE id = ?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카테고리 ID")
    private Long categoryId;

    @Comment("등록한 사람")
    private Long farmerId; //등록한 사람

    @Comment("상품명")
    private String name;

    @Comment("상품 설명")
    private String description;

    @Comment("이미지 URL")
    private String imageUrl;

    @Comment("재고")
    private Integer stock;

    @Comment("가격")
    private Long price;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "entityId")
    List<Review> reviewList = new ArrayList<>();

    @Comment("평균 평점")
    private double averageRating = 0.0;

    @Builder
    public Product(Long productId,
            Long categoryId,
            Long farmerId,
            String name,
            String description,
            String imageUrl,
            Integer stock,
            Long price) {
        this.categoryId = categoryId;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.price = price;
    }

    public Product modify(ProductRegisterRequest productRegisterRequest) {
        this.categoryId = productRegisterRequest.categoryId();
        this.name = productRegisterRequest.name();
        this.description = productRegisterRequest.description();
        this.imageUrl = productRegisterRequest.imageUrl();
        this.stock = productRegisterRequest.stock();
        this.price = productRegisterRequest.price();
        return this;
    }

    public void addStock(Integer stock) {
        this.stock += stock;
    }

    public void addReview(Review pReview) {
        this.reviewList.add(pReview);
        this.averageRating = reviewList.stream()
                .mapToDouble(Review::getRating) // 각 리뷰의 평점을 double로 변환
                .average() // 평균 계산
                .orElse(0.0);
    }

}
