package poomasi.domain.product.service;

import lombok.Builder;
import poomasi.domain.product.entity.Product;

@Builder
public record ProductResponse(
        long id,
        String name,
        Long price,
        int stock,
        String description,
        String imageUrl,
        long categoryId
) {
    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategoryId())
                .build();
    }
}
