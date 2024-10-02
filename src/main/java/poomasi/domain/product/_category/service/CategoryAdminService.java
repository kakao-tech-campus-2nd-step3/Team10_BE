package poomasi.domain.product._category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poomasi.domain.product._category.dto.CategoryRequest;
import poomasi.domain.product._category.entity.Category;
import poomasi.domain.product._category.repository.CategoryRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class CategoryAdminService {
    private final CategoryRepository categoryRepository;

    public Long registerCategory(CategoryRequest categoryRequest) {
        //admin인지 확인

        Category category = categoryRequest.toEntity();
        category = categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    public void modifyCategory(Long categoryId, CategoryRequest categoryRequest) {
        //admin인지 확인

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(BusinessError.CATEGORY_NOT_FOUND));
        category.modifyName(categoryRequest);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        //admin인지 확인
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(BusinessError.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }
}
