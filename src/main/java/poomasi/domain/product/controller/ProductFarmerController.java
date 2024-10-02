package poomasi.domain.product.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.service.ProductFarmerService;

@RestController
@RequiredArgsConstructor
public class ProductFarmerController {

    private final ProductFarmerService productFarmerService;

    @PostMapping("/api/products")
    public ResponseEntity<?> registerProduct(@RequestBody ProductRegisterRequest product) {
        Long productId = productFarmerService.registerProduct(product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/api/products/{productId}")
    public ResponseEntity<?> modifyProduct(@RequestBody ProductRegisterRequest product,
                                           @PathVariable Long productId) {
        productFarmerService.modifyProduct(product, productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }

    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productFarmerService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/products/{productId}/count/{quantity}")
    public ResponseEntity<?> addQuantity(@PathVariable Long productId,
                                         @PathVariable Integer quantity) {
        productFarmerService.addQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
