package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.dto.UpdateProductQuantityRequest;
import poomasi.domain.product.service.ProductFarmerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Slf4j
public class ProductFarmerController {

    private final ProductFarmerService productFarmerService;

    @Secured("ROLE_FARMER")
    @PostMapping("")
    public ResponseEntity<?> registerProduct
            (@AuthenticationPrincipal UserDetailsImpl userDetails,
                    @RequestBody ProductRegisterRequest product) {
        Member member = userDetails.getMember();
        Long productId = productFarmerService.registerProduct(member, product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @Secured("ROLE_FARMER")
    @PutMapping("/{productId}")
    public ResponseEntity<?> modifyProduct(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProductRegisterRequest product,
            @PathVariable Long productId) {
        Member member = userDetails.getMember();
        productFarmerService.modifyProduct(member, product, productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @Secured("ROLE_FARMER")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId) {
        Member member = userDetails.getMember();
        productFarmerService.deleteProduct(member, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Secured("ROLE_FARMER")
    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProductQuantity(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId,
            @RequestBody UpdateProductQuantityRequest request) {
        log.debug("Product ID: {}", productId);
        log.debug("Update Request: {}", request);
        Member member = userDetails.getMember();
        productFarmerService.addQuantity(member, productId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
