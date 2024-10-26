package coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.OptionDto;
import com.example.coursefordevelopment.entity.Option;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionMapper {
    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

    OptionDto optionToOptionDto(Option option);

    Option optionDtoToOption(OptionDto optionDto);
}
