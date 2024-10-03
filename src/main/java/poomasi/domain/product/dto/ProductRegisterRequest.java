package poomasi.domain.product.dto;

import poomasi.domain.category.entity.Category;
import poomasi.domain.product.entity.Product;

public record ProductRegisterRequest(
        Long categoryId,
        Long farmerId, //등록한 사람
        String name,
        String description,
        String imageUrl,
        int quantity,
        int price
) {

    public Product toEntity(Category category) {
        return Product.builder()
                .category(category)
                .farmerId(farmerId)
                .name(name)
                .description(description)
                .imageUrl(imageUrl)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
