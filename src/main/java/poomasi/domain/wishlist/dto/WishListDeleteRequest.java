package poomasi.domain.wishlist.dto;

public record WishListDeleteRequest(
        Long memberId,
        Long productId
) {
}
