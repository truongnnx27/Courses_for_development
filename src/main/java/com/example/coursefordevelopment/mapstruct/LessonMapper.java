package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.LessonDto;
import com.example.coursefordevelopment.entity.Lesson;
import com.example.coursefordevelopment.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface LessonMapper {

    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    @Mapping(source = "lessonType.id", target = "lessonTypeId")
    @Mapping(source = "session.id", target = "sessionId")
    LessonDto lessonToLessonDto(Lesson lesson);

    @Mapping(source = "lessonTypeId", target = "lessonType.id")
    @Mapping(source = "sessionId", target = "session.id")
    Lesson lessonDtoToLesson(LessonDto lessonDto);


}
