package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.QuestionDto;
import com.example.coursefordevelopment.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(source = "options", target = "options") // Ánh xạ options
    QuestionDto questionToQuestionDto(Question question);

    @Mapping(source = "options", target = "options") // Ánh xạ options
    Question questionDtoToQuestion(QuestionDto questionDto);


}
