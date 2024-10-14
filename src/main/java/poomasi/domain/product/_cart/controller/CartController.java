package poomasi.domain.product._cart.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import poomasi.domain.product._cart.dto.CartRegisterRequest;
import poomasi.domain.product._cart.dto.CartRequest;
import poomasi.domain.product._cart.dto.CartResponse;
import poomasi.domain.product._cart.service.CartService;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //장바구니 정보
    @GetMapping("/api/cart")
    public ResponseEntity<?> getCart() {
        List<CartResponse> cart = cartService.getCart();
        return ResponseEntity.ok().body(cart);
    }

    //장바구니 선택한거만 가격
    @GetMapping("/api/cart/price")
    public ResponseEntity<?> getPrice() {
        Integer price = cartService.getPrice();
        return ResponseEntity.ok().body(price);
    }

    //장바구니 추가
    @PostMapping("/api/cart")
    public ResponseEntity<?> addCart(@RequestBody CartRegisterRequest cartRequest) {
        Long cartId = cartService.addCart(cartRequest);
        return new ResponseEntity<>(cartId, HttpStatus.CREATED);
    }

    //장바구니 선택/해제
    @PostMapping("/api/cart/select")
    public ResponseEntity<?> changeSelect(@RequestBody CartRequest cartRequest) {
        cartService.changeSelect(cartRequest);
        return ResponseEntity.ok().build();
    }

    //장바구니 삭제
    @DeleteMapping("/api/cart")
    public ResponseEntity<?> removeCart(@RequestBody CartRequest cartRequest) {
        cartService.deleteCart(cartRequest);
        return ResponseEntity.ok().build();
    }

    //장바구니 선택된거 삭제
    @DeleteMapping("/api/cart/selected")
    public ResponseEntity<?> removeSelected() {
        cartService.removeSelected();
        return ResponseEntity.ok().build();
    }

    //장바구니 전부 삭제
    @DeleteMapping("/api/cart/all")
    public ResponseEntity<?> removeAllCart() {
        cartService.deleteAll();
        return ResponseEntity.ok().build();
    }

    //장바구니 물건 개수 추가
    @PatchMapping("/api/cart/add")
    public ResponseEntity<?> addCount(@RequestBody CartRequest cartRequest) {
        cartService.addCount(cartRequest);
        return ResponseEntity.ok().build();
    }

    //장바구니 물건 개수 감소
    @PatchMapping("/api/cart/sub")
    public ResponseEntity<?> subCount(@RequestBody CartRequest cartRequest) {
        cartService.subCount(cartRequest);
        return ResponseEntity.ok().build();
    }
}
