package poomasi.domain.review.dto;

import poomasi.domain.product.entity.Product;
import poomasi.domain.review.entity.ProductReview;

public record ProductReviewRequest(
        float rating,
        String content
) {

    public ProductReview toEntity(Product product) {
        return new ProductReview(this.rating, this.content, product);
    }
}
