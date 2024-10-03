package poomasi.domain.product.dto;

import java.util.List;
import poomasi.domain.product.entity.Product;
import poomasi.domain.review.dto.ProductReviewResponse;

public record ProductResponse(
        long id,
        long categoryId,
        //long farmerId,
        String name,
        String description,
        String imageUrl,
        int quantity,
        int price,
        List<ProductReviewResponse> reviewList,
        float averageRating
) {

    public static ProductResponse fromEntity(Product product) {
        final float[] sum = {0.0f};
        final int[] cnt = {0};

        List<ProductReviewResponse> reviews = product.getReviewList().stream()
                .map(productReview -> {
                    sum[0] += productReview.getRating(); // 평점 합계 계산
                    cnt[0]++; // 리뷰 개수 증가
                    return ProductReviewResponse.fromEntity(productReview); // 각 리뷰를 응답 객체로 변환
                })
                .toList();

        float averageRating = 0.0f;
        if (cnt[0] > 0) {
            averageRating = (sum[0] / cnt[0]);
        }

        return new ProductResponse(
                product.getId(),
                product.getCategory().getId(),
                //product.getFarmerId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getQuantity(),
                product.getPrice(),
                reviews,
                averageRating
        );
    }
}
