package poomasi.domain.product.dto;

import lombok.Builder;
import poomasi.domain.product.entity.Product;

@Builder
public record ProductResponse(
        Long id,
        String name,
        Long price,
        Integer stock,
        String description,
        String imageUrl,
        Long categoryId
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
