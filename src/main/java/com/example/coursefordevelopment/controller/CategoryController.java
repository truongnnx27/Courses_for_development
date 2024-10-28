package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.CategoryDto;
import com.example.coursefordevelopment.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getCategorys")
    public ResponseEntity<List<CategoryDto>> getCategorys(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/postCategory")
    public ResponseEntity<CategoryDto> postCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PutMapping("/putCategory/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }
}
