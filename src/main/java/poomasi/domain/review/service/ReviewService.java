package poomasi.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.farm.entity.Farm;
import poomasi.domain.farm.repository.FarmRepository;
import poomasi.domain.member.entity.Member;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.domain.review.dto.ReviewRequest;
import poomasi.domain.review.entity.EntityType;
import poomasi.domain.review.entity.Review;
import poomasi.domain.review.repository.ReviewRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final FarmRepository farmRepository;

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

        deleteReviewFromEntity(review);
        reviewRepository.delete(review);
    }

    private void deleteReviewFromEntity(Review review) {
        Long entityId = review.getEntityId();
        EntityType entityType = review.getEntityType();

        if (entityType == EntityType.FARM) {
            Farm farm = getFarmById(entityId);
            farm.getReviewList().remove(review);
        }
        if (entityType == EntityType.PRODUCT) {
            Product product = getProductById(entityId);
            product.getReviewList().remove(review);
        }
    }

    private Product getProductById(Long entityId) {
        return productRepository.findById(entityId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

    private Farm getFarmById(Long entityId) {
        return farmRepository.findById(entityId)
                .orElseThrow(() -> new BusinessException(BusinessError.FARM_NOT_FOUND));
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
