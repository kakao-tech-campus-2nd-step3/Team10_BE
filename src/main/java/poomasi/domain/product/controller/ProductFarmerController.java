package poomasi.domain.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.service.ProductFarmerService;

@RestController
@AllArgsConstructor
public class ProductFarmerController {
    private final ProductFarmerService productFarmerService;

    @PostMapping("/api/products")
    public ResponseEntity<?> registerProduct(@RequestHeader("Authorization") String token, @RequestBody ProductRegisterRequest product) {
        Long productId = productFarmerService.registerProduct(token, product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/api/products/{product_id}")
    public ResponseEntity<?> modifyProduct(@RequestHeader("Authorization") String token, @RequestBody ProductRegisterRequest product, @PathVariable("product_id") Long productId) {
        productFarmerService.modifyProduct(token, product, productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @DeleteMapping("/api/products/{product_id}")
    public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String token, @PathVariable("product_id") Long productId) {
        productFarmerService.deleteProduct(token, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/products/{product_id}/count/{quantity}")
    public ResponseEntity<?> addQuantity(@RequestHeader("Authorization") String token, @PathVariable("product_id") Long productId, @PathVariable("quantity") Integer quantity) {
        productFarmerService.addQuantity(token, productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
