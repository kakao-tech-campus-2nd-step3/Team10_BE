package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.product.service.ProductAdminService;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductAdminService productAdminService;

//    @GetMapping("/api/products/{productId")
//    public ResponseEntity<?> getProducts(@PathVariable int productId) {
//
//    }

}
