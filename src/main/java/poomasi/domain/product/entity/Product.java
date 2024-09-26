package poomasi.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.UpdateTimestamp;
import poomasi.domain.product.dto.ProductRegisterRequest;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE farm SET deleted = true WHERE id = ?")
@SQLSelect(sql = "SELECT * FROM farm WHERE status = ProductStatus.OPEN")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private Long farmerId; //등록한 사람
    private String name;
    private String description;
    private String imageUrl;
    private int quantity;
    private String price;
    private ProductStatus status = ProductStatus.PENDING;
    private boolean deleted = Boolean.FALSE;

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

    public void close() {
        this.status = ProductStatus.CLOSED;
    }

    public void open() {
        this.status = ProductStatus.OPEN;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
