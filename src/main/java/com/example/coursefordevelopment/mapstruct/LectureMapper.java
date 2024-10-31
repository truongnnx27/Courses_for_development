package com.example.coursefordevelopment.mapstruct;


import com.example.coursefordevelopment.dto.LectureDto;
import com.example.coursefordevelopment.entity.Lecture;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    LectureMapper INSTANCE = Mappers.getMapper(LectureMapper.class);

    LectureDto lectureToLectureDto(Lecture lecture);

    Lecture lectureDtoToLecture(LectureDto lectureDto);

    List<LectureDto> listLectureToListLectureDto(List<Lecture> lecture);
}
