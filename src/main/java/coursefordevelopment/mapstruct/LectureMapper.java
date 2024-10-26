package coursefordevelopment.mapstruct;


import com.example.coursefordevelopment.dto.LectureDto;
import com.example.coursefordevelopment.entity.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    LectureMapper INSTANCE = Mappers.getMapper(LectureMapper.class);

    LectureDto lectureToLectureDto(Lecture lecture);

    Lecture lectureDtoToLecture(LectureDto lectureDto);

}
