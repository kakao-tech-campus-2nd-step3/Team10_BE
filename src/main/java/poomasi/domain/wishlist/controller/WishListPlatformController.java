package poomasi.domain.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.wishlist.dto.WishListDeleteRequest;
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

    @PostMapping("/delete")
    public ResponseEntity<?> deleteWishList(@RequestBody WishListDeleteRequest request) {
        wishListPlatformService.deleteWishList(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<?> findWishListByMemberId(@RequestBody Long memberId) {
        // FIXME : memberID는 SecurityContextHolder에서 가져오도록 수정
        return ResponseEntity.ok(wishListPlatformService.findWishListByMemberId(memberId));
    }
}
