package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CourseLevelDto;
import com.example.coursefordevelopment.entity.CourseLevel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CourseLevelMapper {

    CourseLevelMapper INSTANCE = Mappers.getMapper(CourseLevelMapper.class);

    CourseLevelDto courseLevelToCourseLevelDto(CourseLevel courseLevel);

    CourseLevel courseLevelDtoToCourseLevel(CourseLevelDto courseLevelDto);
}
