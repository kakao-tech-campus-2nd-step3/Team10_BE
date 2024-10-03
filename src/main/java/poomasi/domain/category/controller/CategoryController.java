package poomasi.domain.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import poomasi.domain.category.dto.CategoryResponse;
import poomasi.domain.category.dto.ProductListInCategoryResponse;
import poomasi.domain.category.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/api/categories/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable long categoryId) {
        List<ProductListInCategoryResponse> productList = categoryService.getProductInCategory(
                categoryId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
