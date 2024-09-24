package poomasi.domain.category.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.category.dto.CategoryResponse;
import poomasi.domain.category.entity.Category;
import poomasi.domain.category.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }
}
