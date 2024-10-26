package coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.SectionDto;
import com.example.coursefordevelopment.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectionMapper {
    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);

    SectionDto sectionToSectionDto(Section section);

    Section sectionDtoToSection(SectionDto sectionDto);
}
