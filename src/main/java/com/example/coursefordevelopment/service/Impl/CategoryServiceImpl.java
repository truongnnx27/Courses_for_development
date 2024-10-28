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
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable).map(categoryMapper::CategoryToCategoryDto);
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        return null;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.CategoryDtoToCategory(categoryDto);
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void deleteCategoryById(int id) {

    }
}
