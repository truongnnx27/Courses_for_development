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

    @Mapping(source = "lesson.id", target = "lessonId")
    QuizDto quizToQuizDto(Quiz quiz);

    @Mapping(source = "lessonId", target = "lesson.id")
    Quiz quizDtoToQuiz(QuizDto quizDto);

}
