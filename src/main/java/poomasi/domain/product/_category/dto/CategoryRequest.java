package poomasi.domain.product._category.dto;

import poomasi.domain.product._category.entity.Category;

public record CategoryRequest(String name) {
    public Category toEntity() {
        return new Category(name);
    }
}
