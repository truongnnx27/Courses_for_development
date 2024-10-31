package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.SectionDto;
import com.example.coursefordevelopment.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SectionMapper {
    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);

    SectionDto sectionToSectionDto(Section section);

    Section sectionDtoToSection(SectionDto sectionDto);

    List<SectionDto> sectionsToSectionDtos(List<Section> sections);
}
