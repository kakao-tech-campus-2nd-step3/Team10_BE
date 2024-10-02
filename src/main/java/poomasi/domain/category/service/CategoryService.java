package poomasi.domain.category.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.category.dto.CategoryResponse;
import poomasi.domain.category.entity.Category;
import poomasi.domain.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }
}
