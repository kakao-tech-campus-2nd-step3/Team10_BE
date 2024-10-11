package poomasi.domain.wishlist.dto.request;

import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;
import poomasi.domain.wishlist.entity.WishList;

public record WishListAddRequest(
        Long memberId,
        Long productId
) {
    public WishList toEntity(Member member, Product product) {
        return WishList.builder()
                .member(member)
                .product(product)
                .build();
    }
}
