package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.QuizDto;
import com.example.coursefordevelopment.entity.Question;
import com.example.coursefordevelopment.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface QuizMapper {

    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    // Ánh xạ Quiz -> QuizDto (bao gồm cả lessonId và danh sách câu hỏi)
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "questions", target = "questions") // Thêm ánh xạ cho questions
    QuizDto quizToQuizDto(Quiz quiz);

    // Ánh xạ QuizDto -> Quiz (bao gồm cả lessonId và danh sách câu hỏi)
    @Mapping(source = "lessonId", target = "lesson.id")
    @Mapping(source = "questions", target = "questions") // Thêm ánh xạ cho questions
    Quiz quizDtoToQuiz(QuizDto quizDto);

}
