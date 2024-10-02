package poomasi.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.product.dto.ProductRegisterRequest;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET deletedAt = current_timestamp WHERE id = ?")
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

    @Comment("수량")
    private int quantity;

    @Comment("가격")
    private String price;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Builder
    public Product(Long productId,
                   Long categoryId,
                   Long farmerId, //등록한 사람
                   String name,
                   String description,
                   String imageUrl,
                   int quantity,
                   String price) {
        this.categoryId = categoryId;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    public Product modify(ProductRegisterRequest productRegisterRequest) {
        this.categoryId = productRegisterRequest.categoryId();
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

}
