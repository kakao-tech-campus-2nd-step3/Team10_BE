package poomasi.domain.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.wishlist.dto.request.WishListAddRequest;
import poomasi.domain.wishlist.service.WishListPlatformService;

@RequestMapping("/api/v1/wish-list")
@RestController
@RequiredArgsConstructor
public class WishListPlatformController {
    private final WishListPlatformService wishListPlatformService;

    @PostMapping("/add")
    public ResponseEntity<?> addWishList(@RequestBody WishListAddRequest request) {
        wishListPlatformService.addWishList(request);
        return ResponseEntity.ok().build();
    }
}
