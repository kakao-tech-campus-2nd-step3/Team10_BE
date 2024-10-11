package poomasi.domain.wishlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.wishlist.dto.request.WishListAddRequest;

@RequiredArgsConstructor
@Service
public class WishListPlatformService {
    private final WishListService wishListService;

    @Transactional
    public void addWishList(WishListAddRequest request) {
        wishListService.addWishList(request);
    }
}
