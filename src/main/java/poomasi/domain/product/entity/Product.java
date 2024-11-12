package poomasi.domain.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.order.entity._product.OrderedProduct;
import poomasi.domain.store.entity.Store;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.review.entity.Review;
import poomasi.domain.store.entity.Store;

@Entity
@Getter
@NoArgsConstructor
//@SQLDelete(sql = "UPDATE product SET deleted_at = current_timestamp WHERE id = ?")
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

    @Setter
    @Comment("이미지 URL")
    private String imageUrl;

    @Comment("재고")
    private Integer stock;

    @Comment("가격")
    private BigDecimal price;

    @Comment("재배 환경")
    private String growEnv;

    @Comment("배송비")
    BigDecimal shippingFee;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entityId")
    List<Review> reviewList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "store_id")  // 외래 키 컬럼 지정
    private Store store;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "enum_value")
    @Enumerated(EnumType.STRING)
    List<ProductTagEnum> tags = new ArrayList<>();

    @Comment("평균 평점")
    private double averageRating = 0.0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_product_details_id")
    private List<OrderedProduct> orderProductDetails;

//    @PreRemove
//    public void preRemove() {
//        // Product가 삭제되기 전에 연관된 이미지를 삭제
//        for (Image image : images) {
//            image.setDeletedAt(LocalDateTime.now());
//        }
//    }

    @Builder
    public Product(Long productId,
            Long categoryId,
            Long farmerId,
            String name,
            String description,
            String imageUrl,
            Integer stock,
            BigDecimal price,
            Store store,
            String growEnv,
            BigDecimal shippingFee) {
        this.id = productId;
        this.categoryId = categoryId;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.price = price;
        this.store = store;
        this.growEnv = growEnv;
        this.shippingFee = shippingFee;
    }

    public Product modify(ProductRegisterRequest productRegisterRequest) {
        this.categoryId = productRegisterRequest.categoryId();
        this.name = productRegisterRequest.name();
        this.description = productRegisterRequest.description();
        this.imageUrl = productRegisterRequest.imageUrl();
        this.stock = productRegisterRequest.stock();
        this.price = productRegisterRequest.price();
        this.growEnv = productRegisterRequest.growEnv();
        this.shippingFee = productRegisterRequest.shippingFee();
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

    public void subtractStock(Integer stock) {
        this.stock -= stock;
    }


}
