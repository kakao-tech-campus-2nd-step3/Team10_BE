package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.product.service.ProductAdminService;

@RestController
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PutMapping("/api/products/{product_id}/open")
    ResponseEntity<?> openProduct(@PathVariable("product_id") Long productId) {
        productAdminService.openProduct(productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }
}
