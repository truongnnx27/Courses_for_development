package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.LessonTypeDto;
import com.example.coursefordevelopment.entity.LessonType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessonTypeMapper {

    LessonTypeMapper INSTANCE = Mappers.getMapper(LessonTypeMapper.class);

    LessonTypeDto lessonTypeToLessonTypeDto(LessonType lessonType);

    LessonType lessonTypeDtoToLessonType(LessonTypeDto lessonTypeDto);
}
