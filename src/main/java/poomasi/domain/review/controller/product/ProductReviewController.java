package poomasi.domain.review.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.review.dto.ReviewRequest;
import poomasi.domain.review.dto.ReviewResponse;
import poomasi.domain.review.service.product.ProductReviewService;

@Controller
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @GetMapping("/api/products/{productId}/reviews")
    public ResponseEntity<?> getProductReviews(@PathVariable Long productId) {
        List<ReviewResponse> response = productReviewService.getProductReview(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/products/{productId}/reviews")
    public ResponseEntity<?> registerProductReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId,
            @RequestBody ReviewRequest reviewRequest) {
        Member member = userDetails.getMember();
        Long reviewId = productReviewService.registerProductReview(
                member, productId, reviewRequest);
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }
}
