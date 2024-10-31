package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.CategoryDto;
import com.example.coursefordevelopment.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto);
    void deleteCategoryById(Long id);
    List<CategoryResponse> getCategoriesNumberUser();
}
