package poomasi.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.review.dto.ReviewRequest;
import poomasi.domain.review.entity.Review;
import poomasi.domain.review.repository.ReviewRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void modifyReview(Long reviewId, ReviewRequest reviewRequest) {
        Review pReview = getReviewById(reviewId);
        pReview.modifyReview(reviewRequest);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        reviewRepository.delete(review);
    }

    private Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(BusinessError.REVIEW_NOT_FOUND));
    }
}
