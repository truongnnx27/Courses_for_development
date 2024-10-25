package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.QuizDto;
import com.example.coursefordevelopment.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuizMapper {

    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    @Mapping(source = "questions", target = "questions")
    QuizDto quizToQuizDto(Quiz quiz);

    @Mapping(source = "questions", target = "questions")
    Quiz quizDtoToQuiz(QuizDto quizDto);

}
