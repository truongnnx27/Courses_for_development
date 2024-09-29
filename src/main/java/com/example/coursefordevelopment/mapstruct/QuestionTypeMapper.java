package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.QuestionTypeDto;
import com.example.coursefordevelopment.entity.QuestionType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface QuestionTypeMapper {
    QuestionTypeMapper INSTANCE = Mappers.getMapper(QuestionTypeMapper.class);

    QuestionTypeDto questionTypeToQuestionTypeDto(QuestionType questionType);

    QuestionType questionTypeDtoToQuestionType(QuestionTypeDto questionTypeDto);
}
