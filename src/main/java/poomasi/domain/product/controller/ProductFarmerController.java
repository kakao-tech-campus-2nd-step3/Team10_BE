package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.dto.UpdateProductQuantityRequest;
import poomasi.domain.product.service.ProductFarmerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Slf4j
public class ProductFarmerController {

    private final ProductFarmerService productFarmerService;

    @PostMapping("")
    public ResponseEntity<?> registerProduct(@RequestBody ProductRegisterRequest product) {
        Long productId = productFarmerService.registerProduct(product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> modifyProduct(@RequestBody ProductRegisterRequest product,
                                           @PathVariable Long productId) {
        productFarmerService.modifyProduct(product, productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        // TODO: farmerId를 SecurityContextHolder에서 가져와서 비교해야함.

        productFarmerService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProductQuantity(@PathVariable Long productId,
                                                   @RequestBody UpdateProductQuantityRequest request) {
        log.debug("Product ID: {}", productId);
        log.debug("Update Request: {}", request);
        productFarmerService.addQuantity(productId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
