package poomasi.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import poomasi.domain.review.dto.ProductReviewRequest;
import poomasi.domain.review.service.ProductReviewCustomerService;

@Controller
@RequiredArgsConstructor
public class ProductReviewCustomerController {

    private final ProductReviewCustomerService productReviewCustomerService;

    @PostMapping("/api/products/{productId}/reviews")
    public ResponseEntity<?> registerProductReview(@PathVariable int productId,
            @RequestBody ProductReviewRequest productReviewRequest) {
        long reviewId = productReviewCustomerService.registerReview(productId,
                productReviewRequest);
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/reviews/{reviewId}")
    public ResponseEntity<?> deleteProductReview(@PathVariable long reviewId) {
        productReviewCustomerService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
