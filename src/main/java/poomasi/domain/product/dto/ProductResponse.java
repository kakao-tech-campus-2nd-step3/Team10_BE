package poomasi.domain.product.dto;

import java.util.List;
import poomasi.domain.product.entity.Product;
import poomasi.domain.review.entity.ProductReview;

public record ProductResponse (
        long id,
        long categoryId,
        long farmerId,
        String name,
        String description,
        String imageUrl,
        int quantity,
        int price,
        List<ProductReview> reviewList
){
    public ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCategory().getId(),
                product.getFarmerId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getQuantity(),
                product.getPrice(),
                product.getReviewList()
        );
    }
}
