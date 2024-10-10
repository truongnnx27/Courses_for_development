package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.LessonDto;
import com.example.coursefordevelopment.dto.Request.AppException;
import com.example.coursefordevelopment.entity.Lesson;
import com.example.coursefordevelopment.entity.LessonType;
import com.example.coursefordevelopment.exception.ErrorCode;
import com.example.coursefordevelopment.mapstruct.LessonMapper;
import com.example.coursefordevelopment.mapstruct.LessonTypeMapper;
import com.example.coursefordevelopment.reponsitory.LessonRepository;
import com.example.coursefordevelopment.reponsitory.LessonTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService {

    LessonRepository lessonRepository;
    LessonTypeRepository lessonTypeRepository;

    LessonMapper lessonMapper = LessonMapper.INSTANCE;
    LessonTypeMapper lessonTypeMapper = LessonTypeMapper.INSTANCE;

    public LessonDto createLesson(LessonDto lessonDto)
    {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);

        //lấy lesonType
        LessonType lessonType = lessonTypeRepository.findById(lessonDto.getLessonTypeId())
                .orElseThrow(()-> new AppException(ErrorCode.LESSON_Type_NOT_FOUND));

        lesson.setLessonType(lessonType);
        Lesson saveLesson = lessonRepository.save(lesson);

        return lessonMapper.lessonToLessonDto(saveLesson);
    }

    public LessonDto updateLesson(LessonDto lessonDto,Long id)
    {
        Lesson existingLesson = lessonRepository.findById(id)
                                                .orElseThrow(()-> new AppException(ErrorCode.LESSON_NOT_FOUND));

        existingLesson.setTitle(lessonDto.getTitle());
        existingLesson.setContent(lessonDto.getContent());
        existingLesson.setDuration(lessonDto.getDuration());
        existingLesson.setNumberOfAttachments(lessonDto.getNumberOfAttachments());

        //lấy lessontype
        LessonType lessonType= lessonTypeRepository.findById(lessonDto.getLessonTypeId())
                .orElseThrow(()-> new AppException(ErrorCode.LESSON_Type_NOT_FOUND));
        existingLesson.setLessonType(lessonType);

        Lesson updateLesson = lessonRepository.save(existingLesson);
        return lessonMapper.lessonToLessonDto(updateLesson);
    }

    public List<LessonDto> getAllLesson()
    {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream().map(lessonMapper:: lessonToLessonDto).collect(Collectors.toList());
    }


}
