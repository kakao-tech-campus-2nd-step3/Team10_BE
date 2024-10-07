package poomasi.domain.product.dto;

import poomasi.domain.product.entity.Product;

public record ProductRegisterRequest(
        Long categoryId,
        Long farmerId, //등록한 사람
        String name,
        String description,
        String imageUrl,
        Integer stock,
        Long price
) {

    public Product toEntity() {
        return Product.builder()
                .categoryId(categoryId)
                .farmerId(farmerId)
                .name(name)
                .stock(stock)
                .description(description)
                .imageUrl(imageUrl)
                .stock(stock)
                .price(price)
                .build();
    }
}
