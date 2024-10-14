package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.QuestionDto;
import com.example.coursefordevelopment.entity.Option;
import com.example.coursefordevelopment.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    // Ánh xạ Question -> QuestionDto (bao gồm cả options)
    @Mapping(source = "quiz.id", target = "quizId")
    @Mapping(source = "questionType.id", target = "questionTypeId")
    @Mapping(source = "options", target = "options") // Ánh xạ options
    QuestionDto questionToQuestionDto(Question question);

    // Ánh xạ QuestionDto -> Question (bao gồm cả options)
    @Mapping(source = "quizId", target = "quiz.id")
    @Mapping(source = "questionTypeId", target = "questionType.id")
    @Mapping(source = "options", target = "options") // Ánh xạ options
    Question questionDtoToQuestion(QuestionDto questionDto);


}
