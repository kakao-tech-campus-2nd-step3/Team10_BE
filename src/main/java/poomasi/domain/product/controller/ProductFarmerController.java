package poomasi.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.product.dto.ProductRegisterRequest;
import poomasi.domain.product.service.ProductFarmerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
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
        productFarmerService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{productId}/count/{quantity}") // FIXME: ResponseBody로 수정해야할듯.
    public ResponseEntity<?> addQuantity(@PathVariable Long productId,
                                         @PathVariable Integer quantity) {
        productFarmerService.addQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
