package poomasi.domain.category.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.category.dto.CategoryRequest;
import poomasi.domain.category.dto.CategoryResponse;
import poomasi.domain.category.service.CategoryAdminService;
import poomasi.domain.category.service.CategoryService;

import java.util.List;

@Controller
@AllArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;

    @PostMapping("/api/categories")
    public ResponseEntity<?> registerCategory(@RequestHeader("Authorization") String token, @RequestBody CategoryRequest categoryRequest) {
        Long categoryId = categoryAdminService.registerCategory(token, categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @PutMapping("/api/categories/{category_id}")
    public ResponseEntity<?> modifyCategory(@RequestHeader("Authorization") String token, @PathVariable("category_id") Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        categoryAdminService.modifyCategory(token, categoryId, categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.OK);
    }

    @DeleteMapping("/api/categories/{category_id}")
    public ResponseEntity<?> deleteCategory(@RequestHeader("Authorization") String token, @PathVariable("category_id") Long categoryId) {
        categoryAdminService.deleteCategory(token, categoryId);
        return new ResponseEntity<>(categoryId, HttpStatus.OK);
    }
}
