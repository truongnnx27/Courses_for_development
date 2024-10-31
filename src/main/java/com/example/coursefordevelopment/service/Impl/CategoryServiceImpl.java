package com.example.coursefordevelopment.service.Impl;


import com.example.coursefordevelopment.dto.CategoryDto;
import com.example.coursefordevelopment.dto.response.CategoryResponse;
import com.example.coursefordevelopment.entity.Category;
import com.example.coursefordevelopment.mapstruct.CategoryMapper;
import com.example.coursefordevelopment.reponsitory.CategoryRepository;
import com.example.coursefordevelopment.service.CategoryService;
import com.example.coursefordevelopment.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private ImageService imageService;

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

    @Override
    public List<CategoryResponse> getCategoriesNumberUser() {
        List<Object[]> results = categoryRepository.finnCateNumberUser();
        List<CategoryResponse> categoryResponses = results.stream()
                .map(result -> new CategoryResponse(
                    (String) result[0],
                    (String) result[1],
                    (Long) result[2]
                )).collect(Collectors.toList());
        categoryResponses.forEach( categoryResponse -> {
            try {
                categoryResponse.setCoverImage(imageService.base64Image(categoryResponse.getCoverImage(), "src/main/resources/static/images/"));
            } catch (IOException e) {
                categoryResponse.setCoverImage("");
            }
        });
        return categoryResponses;
    }

    public CategoryDto findCategoryById(Long id) {
        return categoryMapper.CategoryToCategoryDto(categoryRepository.findById(id).get());
    }
}
