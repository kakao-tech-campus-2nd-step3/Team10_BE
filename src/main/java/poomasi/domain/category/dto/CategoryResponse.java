package poomasi.domain.category.dto;

import poomasi.domain.category.entity.Category;

public record CategoryResponse(long id, String name) {

    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
