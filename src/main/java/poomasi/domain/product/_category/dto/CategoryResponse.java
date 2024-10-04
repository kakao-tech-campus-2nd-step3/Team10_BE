package poomasi.domain.product._category.dto;

import lombok.Builder;
import poomasi.domain.product._category.entity.Category;

@Builder
public record CategoryResponse(long id, String name) {

    public static CategoryResponse fromEntity(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
