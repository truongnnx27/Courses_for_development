package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.OptionDto;
import com.example.coursefordevelopment.entity.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface OptionMapper {
    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

    // Ánh xạ Option -> OptionDto
    @Mapping(source = "question.id", target = "questionId")
    OptionDto optionToOptionDto(Option option);

    // Ánh xạ OptionDto -> Option
    @Mapping(source = "questionId", target = "question.id")
    Option optionDtoToOption(OptionDto optionDto);
}
