package poomasi.domain.product.entity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.category.entity.Category;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.review.entity.ProductReview;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET deletedAt = current_timestamp WHERE id = ?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카테고리 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Comment("등록한 사람")
    private Long farmerId; //등록한 사람

    @Comment("상품명")
    private String name;

    @Comment("상품 설명")
    private String description;

    @Comment("이미지 URL")
    private String imageUrl;

    @Comment("수량")
    private int quantity;

    @Comment("가격")
    private int price;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product_review", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<ProductReview> reviewList = new ArrayList<>();

    @Builder
    public Product(Long productId,
            Category category,
            Long farmerId, //등록한 사람
            String name,
            String description,
            String imageUrl,
            int quantity,
            int price) {
        this.category = category;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    public Product modify(Category category, ProductRegisterRequest productRegisterRequest) {
        this.category = category;
        this.name = productRegisterRequest.name();
        this.description = productRegisterRequest.description();
        this.imageUrl = productRegisterRequest.imageUrl();
        this.quantity = productRegisterRequest.quantity();
        this.price = productRegisterRequest.price();
        return this;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void addReview(ProductReview pReview) {
        this.reviewList.add(pReview);
    }

}
