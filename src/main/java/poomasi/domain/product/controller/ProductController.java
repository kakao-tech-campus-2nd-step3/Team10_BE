package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.product.dto.ProductResponse;
import poomasi.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<?> getProductsDetail(@PathVariable long productId) {
        ProductResponse productResponse = productService.getProductDetail(productId);
        return ResponseEntity.ok(productResponse);
    }

}
