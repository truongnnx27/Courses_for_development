package coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.CourseDto;
import com.example.coursefordevelopment.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "instructor", source = "instructor.id")
    @Mapping(target = "id", source = "course.id")
    CourseDto courseToCourseDto(Course course);

    @Mapping(target = "instructor.id", source = "instructor")
    @Mapping(target = "id", source = "courseDto.id")
    Course courseDtoToCourse(CourseDto courseDto);

}
