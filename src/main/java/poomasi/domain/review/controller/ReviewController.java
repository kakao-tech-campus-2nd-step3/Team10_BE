package poomasi.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.member.entity.Member;
import poomasi.domain.review.dto.ReviewRequest;
import poomasi.domain.review.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @DeleteMapping("/api/reviews/{reviewId}")
    public ResponseEntity<?> deleteProductReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reviewId) {
        Member member = userDetails.getMember();
        reviewService.deleteReview(member, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/reviews/{reviewId}")
    public ResponseEntity<?> modifyProductReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest) {
        Member member = userDetails.getMember();
        reviewService.modifyReview(member, reviewId, reviewRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
