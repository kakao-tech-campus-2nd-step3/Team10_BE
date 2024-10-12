package poomasi.domain.product._cart.dto;

import lombok.extern.slf4j.Slf4j;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product._cart.entity.Cart;

@Slf4j
public record CartRequest(
        Long productId,
        Long cartId,
        Integer count
){
    public Cart toEntity(Member member){
        return Cart.builder()
                .memberId(member.getId())
                .productId(productId)
                .selected(Boolean.TRUE)
                .count(count)
                .build();
    }
}
