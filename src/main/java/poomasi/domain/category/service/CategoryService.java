package poomasi.domain.category.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poomasi.domain.category.dto.CategoryResponse;
import poomasi.domain.category.dto.ProductListInCategoryResponse;
import poomasi.domain.category.entity.Category;
import poomasi.domain.category.repository.CategoryRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

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

    public List<ProductListInCategoryResponse> getProductInCategory(long categoryId) {
        Category category = getCategory(categoryId);
        return category.getProducts().stream()
                .map(ProductListInCategoryResponse::fromEntity).toList();
    }

    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(BusinessError.CATEGORY_NOT_FOUND));
    }
}
