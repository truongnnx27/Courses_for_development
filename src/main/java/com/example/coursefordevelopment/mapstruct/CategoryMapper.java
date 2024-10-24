package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CategoryDto;
import com.example.coursefordevelopment.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

//    @Mapping(source = "course.id", target = "courseId")
//    CategoryDto categoryToCategoryDto(Category category);
//
//    @Mapping(source = "courseId", target = "course.id")
//    Category categoryDtoToCategory(CategoryDto categoryDto);
}
