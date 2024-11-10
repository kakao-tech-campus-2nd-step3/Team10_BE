package poomasi.domain.product.dto;

import java.math.BigDecimal;
import org.hibernate.annotations.Comment;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;
import poomasi.domain.store.entity.Store;

public record ProductRegisterRequest(
        Long categoryId,
        String name,
        String description,
        String imageUrl,
        Integer stock,
        BigDecimal price,
        @Comment("재배 환경")
        String growEnv,
        BigDecimal shippingFee
) {

    public Product toEntity(Member member, Store store) {
        return Product.builder()
                .categoryId(categoryId)
                .farmerId(member.getId())
                .name(name)
                .stock(stock)
                .description(description)
                .imageUrl(imageUrl)
                .stock(stock)
                .price(price)
                .store(store)
                .growEnv(growEnv)
                .shippingFee(shippingFee)
                .build();
    }
}
