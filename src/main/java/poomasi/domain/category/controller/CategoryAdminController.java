package poomasi.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.category.dto.CategoryRequest;
import poomasi.domain.category.service.CategoryAdminService;

@RestController
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @PostMapping("/api/categories")
    public ResponseEntity<?> registerCategory(@RequestBody CategoryRequest categoryRequest) {
        Long categoryId = categoryAdminService.registerCategory(categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @PutMapping("/api/categories/{category_id}")
    public ResponseEntity<?> modifyCategory(@PathVariable("category_id") Long categoryId,
            @RequestBody CategoryRequest categoryRequest) {
        categoryAdminService.modifyCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.OK);
    }

    @DeleteMapping("/api/categories/{category_id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("category_id") Long categoryId) {
        categoryAdminService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryId, HttpStatus.OK);
    }
}
