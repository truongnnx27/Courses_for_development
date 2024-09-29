package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.SessionDto;
import com.example.coursefordevelopment.entity.Lesson;
import com.example.coursefordevelopment.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(target = "lessonIds", expression = "java(mapLessonIds(session.getLessons()))")
    SessionDto sessionToSessionDto(Session session);

    @Mapping(source = "courseId", target = "course.id")
    @Mapping(target = "lessons", ignore = true)
    Session sessionDtoToSession(SessionDto sessionDto);

    // Phương thức ánh xạ List<Lesson> thành List<Long> (ID)
    default List<Long> mapLessonIds(List<Lesson> lessons) {
        return lessons.stream()
                .map(Lesson::getId)  // Lấy ID của mỗi Lesson
                .collect(Collectors.toList());
    }
}
