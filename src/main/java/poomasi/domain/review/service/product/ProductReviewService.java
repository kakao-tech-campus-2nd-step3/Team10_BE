package poomasi.domain.review.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.domain.review.dto.ReviewRequest;
import poomasi.domain.review.dto.ReviewResponse;
import poomasi.domain.review.entity.EntityType;
import poomasi.domain.review.entity.Review;
import poomasi.domain.review.repository.ReviewRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public List<ReviewResponse> getProductReview(Long productId) {
        getProductByProductId(productId); //상품이 존재하는지 체크

        return reviewRepository.findByProductId(productId).stream()
                .map(ReviewResponse::fromEntity).toList();
    }

    @Transactional
    public Long registerProductReview(Long entityId, ReviewRequest reviewRequest) {
        // s3 이미지 저장하고 주소 받아와서 review에 추가해주기

        Review pReview = reviewRequest.toEntity(entityId);
        pReview.setReviewType(EntityType.PRODUCT);
        pReview = reviewRepository.save(pReview);

        return pReview.getId();
    }

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

}
