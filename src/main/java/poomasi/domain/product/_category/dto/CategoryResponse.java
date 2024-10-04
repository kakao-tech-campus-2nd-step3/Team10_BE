package poomasi.domain.product._category.dto;

import poomasi.domain.product._category.entity.Category;

public record CategoryResponse(long id, String name) {
    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
