package poomasi.domain.category.dto;

import poomasi.domain.category.entity.Category;

public record CategoryRequest(String name) {
    public Category toEntity() {
        return new Category(name);
    }
}
