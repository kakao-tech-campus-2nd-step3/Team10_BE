package poomasi.domain.review.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import poomasi.domain.review.dto.ProductReviewResponse;
import poomasi.domain.review.service.ProductReviewService;

@Controller
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @GetMapping("/api/products/{productId}/reviews")
    public ResponseEntity<?> getProductReviews(@PathVariable long productId) {
        List<ProductReviewResponse> response = productReviewService.getProductReview(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
