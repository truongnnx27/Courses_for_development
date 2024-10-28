package com.example.coursefordevelopment.mapstruct;
import com.example.coursefordevelopment.dto.CategoryDto;
import com.example.coursefordevelopment.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category CategoryDtoToCategory(CategoryDto category);

    CategoryDto CategoryToCategoryDto(Category category);

    List<CategoryDto> ListCategoryToCategoryDtoList(List<Category> categoryList);
}
