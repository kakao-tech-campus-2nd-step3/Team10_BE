package poomasi.domain.wishlist.dto;

import poomasi.domain.wishlist.entity.WishList;

public record WishListResponse(
        Long productId,
        String productName,
        Long price,
        String imageUrl,
        String description
) {
    public static WishListResponse fromEntity(WishList wishList) {
        return new WishListResponse(
                wishList.getProduct().getId(),
                wishList.getProduct().getName(),
                wishList.getProduct().getPrice(),
                wishList.getProduct().getImageUrl(),
                wishList.getProduct().getDescription()
        );
    }
}
