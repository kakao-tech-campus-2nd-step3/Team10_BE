package poomasi.domain.product._category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.product._category.dto.CategoryRequest;
import poomasi.domain.product._category.service.CategoryAdminService;

@RestController
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @PostMapping("/api/categories")
    public ResponseEntity<?> registerCategory(@RequestBody CategoryRequest categoryRequest) {
        Long categoryId = categoryAdminService.registerCategory(categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @PutMapping("/api/categories/{categoryId}")
    public ResponseEntity<?> modifyCategory(@PathVariable Long categoryId,
            @RequestBody CategoryRequest categoryRequest) {
        categoryAdminService.modifyCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.OK);
    }

    @DeleteMapping("/api/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        categoryAdminService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryId, HttpStatus.OK);
    }
}
