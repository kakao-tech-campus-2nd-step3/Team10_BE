package poomasi.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.domain.review.dto.ProductReviewRequest;
import poomasi.domain.review.entity.ProductReview;
import poomasi.domain.review.repository.ProductReviewRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ProductReviewCustomerService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;

    public long registerReview(long productId, ProductReviewRequest productReviewRequest) {
        //이미지 저장하고 주소 받아와서 review에 추가해주기
        Product product = getProductByProductId(productId);
        ProductReview pReview = productReviewRequest.toEntity(product);
        pReview = productReviewRepository.save(pReview);
        product.addReview(pReview);
        return pReview.getId();
    }


    private Product getProductByProductId(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public void deleteReview(long reviewId) {
        ProductReview review = getReviewById(reviewId);
        productReviewRepository.delete(review);
    }

    private ProductReview getReviewById(long reviewId) {
        return productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(BusinessError.REVIEW_NOT_FOUND));
    }
}
