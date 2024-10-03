package poomasi.domain.category.dto;

import poomasi.domain.product.entity.Product;

public record ProductListInCategoryResponse(
        Long categoryId,
        //Long farmerId, //등록한 사람
        String name,
        String description,
        String imageUrl,
        int quantity,
        int price
) {
    public static ProductListInCategoryResponse fromEntity(Product product) {
        return new ProductListInCategoryResponse(
                product.getCategory().getId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getQuantity(),
                product.getPrice()
        );
    }
}
