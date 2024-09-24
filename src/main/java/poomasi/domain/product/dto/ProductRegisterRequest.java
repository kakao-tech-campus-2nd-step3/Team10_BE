package poomasi.domain.product.dto;

import poomasi.domain.product.entity.Product;

public record ProductRegisterRequest(
        Long categoryId,
        Long userId, //등록한 사람
        String name,
        String description,
        String imageUrl,
        int quantity,
        String price
) {
    public Product toEntity() {
        return Product.builder()
                .categoryId(categoryId)
                .userId(userId)
                .name(name)
                .description(description)
                .quantity(0)
                .imageUrl(imageUrl)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
