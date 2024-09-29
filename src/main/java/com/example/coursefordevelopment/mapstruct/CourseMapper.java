package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CourseDto;
import com.example.coursefordevelopment.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "courseLevel.id", target = "courseLevelId")
    CourseDto courseToCourseDto(Course course);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseLevelId", target = "courseLevel.id")
    Course courseDtoToCourse(CourseDto courseDto);
}
