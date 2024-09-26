package poomasi.domain.category.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.category.dto.CategoryRequest;
import poomasi.domain.category.entity.Category;
import poomasi.domain.category.repository.CategoryRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@AllArgsConstructor
public class CategoryAdminService {
    private final CategoryRepository categoryRepository;

    public Long registerCategory(String token, CategoryRequest categoryRequest) {
        //admin인지 확인

        Category category = categoryRequest.toEntity();
        category = categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    public void modifyCategory(String token, Long categoryId, CategoryRequest categoryRequest) {
        //admin인지 확인

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(BusinessError.CATEGORY_NOT_FOUND));
        category.modifyName(categoryRequest);
    }

    @Transactional
    public void deleteCategory(String token, Long categoryId) {
        //admin인지 확인
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(BusinessError.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }
}
