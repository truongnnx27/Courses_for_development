package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CategoryDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryDto> getAllCategories(int page, int size);
    CategoryDto getCategoryById(int id);
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto);
    void deleteCategoryById(int id);
}
