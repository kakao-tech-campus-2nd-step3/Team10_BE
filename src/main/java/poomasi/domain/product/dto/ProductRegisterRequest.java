package poomasi.domain.product.dto;

import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;

public record ProductRegisterRequest(
        Long categoryId,
        String name,
        String description,
        String imageUrl,
        Integer stock,
        Long price
) {

    public Product toEntity(Member member) {
        return Product.builder()
                .categoryId(categoryId)
                .farmerId(member.getId())
                .name(name)
                .stock(stock)
                .description(description)
                .imageUrl(imageUrl)
                .stock(stock)
                .price(price)
                .build();
    }
}
