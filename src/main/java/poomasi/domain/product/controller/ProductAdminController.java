package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.product.service.ProductAdminService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @PutMapping("/{productId}/open")
    ResponseEntity<?> openProduct(@PathVariable Long productId) {
        productAdminService.openProduct(productId);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }
}
