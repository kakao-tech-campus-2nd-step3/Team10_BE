package poomasi.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.product.entity.Product;
import poomasi.domain.product.repository.ProductRepository;
import poomasi.domain.review.dto.ProductReviewRequest;
import poomasi.domain.review.dto.ProductReviewResponse;
import poomasi.domain.review.entity.ProductReview;
import poomasi.domain.review.repository.ProductReviewRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;

    public List<ProductReviewResponse> getProductReview(long productId) {
        getProductByProductId(productId); //상품이 존재하는지 체크

        return productReviewRepository.findByProductId(productId).stream()
                .map(ProductReviewResponse::fromEntity).toList();
    }

    private Product getProductByProductId(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }


}
