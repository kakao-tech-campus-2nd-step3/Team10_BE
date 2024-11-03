package poomasi.domain.review.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.member.entity.Member;
import poomasi.domain.order.entity.OrderProductDetails;
import poomasi.domain.order.entity.OrderStatus;
import poomasi.domain.order.repository.OrderProductDetailsRepository;
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
    private final OrderProductDetailsRepository orderProductDetailsRepository;

    public List<ReviewResponse> getProductReview(Long productId) {
        getProductByProductId(productId); //상품이 존재하는지 체크

        return reviewRepository.findByProductId(productId).stream()
                .map(ReviewResponse::fromEntity).toList();
    }

    @Transactional
    public Long registerProductReview(Member member, Long orderProductDetailId,
            ReviewRequest reviewRequest) {
        // s3 이미지 저장하고 주소 받아와서 review에 추가해주기
        OrderProductDetails orderProductDetails = orderProductDetailsRepository.findById(
                orderProductDetailId).orElseThrow(
                () -> new BusinessException(BusinessError.ORDER_PRODUCT_DETAILS_NOT_FOUND));

        Product product = orderProductDetails.getProduct();
        Member orderMember = orderProductDetails.getOrder().getMember();

        if (!orderMember.getId().equals(member.getId())) {
            throw new BusinessException(BusinessError.MEMBER_ID_MISMATCH);
        }

        if (orderProductDetails.getReviewId() != null) {
            throw new BusinessException(BusinessError.REVIEW_ALREADY_EXIST);
        }

        if (orderProductDetails.getOrderStatus() != OrderStatus.DELIVERED &&
                orderProductDetails.getOrderStatus() != OrderStatus.ORDER_COMPLETE) {
            throw new BusinessException(BusinessError.ORDER_NOT_COMPLETED);
        }

        Review pReview = reviewRequest.toEntity(product.getId(), EntityType.PRODUCT, member);
        pReview = reviewRepository.save(pReview);
        orderProductDetails.setReviewId(pReview.getId());
        product.addReview(pReview);
        return pReview.getId();
    }

    private Product getProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessError.PRODUCT_NOT_FOUND));
    }

}
