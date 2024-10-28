package com.example.coursefordevelopment.service.Impl;


import com.example.coursefordevelopment.dto.CategoryDto;
import com.example.coursefordevelopment.entity.Category;
import com.example.coursefordevelopment.mapstruct.CategoryMapper;
import com.example.coursefordevelopment.reponsitory.CategoryRepository;
import com.example.coursefordevelopment.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.ListCategoryToCategoryDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return null;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.CategoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.CategoryToCategoryDto(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto findCategoryById(Long id) {
        return categoryMapper.CategoryToCategoryDto(categoryRepository.findById(id).get());
    }
}
