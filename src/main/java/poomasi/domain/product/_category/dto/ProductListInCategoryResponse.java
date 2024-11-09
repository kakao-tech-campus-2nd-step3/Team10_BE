package poomasi.domain.product._category.dto;

import java.math.BigDecimal;
import lombok.Builder;
import poomasi.domain.product.entity.Product;

@Builder
public record ProductListInCategoryResponse(
        Long categoryId,
        String name,
        String description,
        String imageUrl,
        Integer quantity,
        BigDecimal price
) {

    public static ProductListInCategoryResponse fromEntity(Product product) {
        return ProductListInCategoryResponse.builder()
                .categoryId(product.getCategoryId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .quantity(product.getStock())
                .price(product.getPrice())
                .build();
    }
}
