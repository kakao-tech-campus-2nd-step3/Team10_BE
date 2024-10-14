package poomasi.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.entity.Member;
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
    public void modifyReview(Member member, Long reviewId, ReviewRequest reviewRequest) {
        Review review = getReviewById(reviewId);
        checkAuth(member, review);
        review.modifyReview(reviewRequest);
    }

    @Transactional
    public void deleteReview(Member member, Long reviewId) {
        Review review = getReviewById(reviewId);
        if (!member.isAdmin()) {
            checkAuth(member, review);
        }
        reviewRepository.delete(review);
    }

    private void checkAuth(Member member, Review review) {
        if (!review.getReviewer().getId().equals(member.getId())) {
            throw new BusinessException(BusinessError.MEMBER_ID_MISMATCH);
        }
    }

    private Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(BusinessError.REVIEW_NOT_FOUND));
    }
}
