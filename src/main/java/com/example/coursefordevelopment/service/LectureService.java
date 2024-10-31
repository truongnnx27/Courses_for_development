package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.LectureDto;

import java.util.List;

public interface LectureService {
    LectureDto getLessonById(Long id);
    List<LectureDto> getAllLessons();
    LectureDto createLesson(LectureDto lectureDto);
    LectureDto updateLesson(Long id, LectureDto lectureDto);
    void deleteLesson(Long id);
    List<LectureDto> getLessonsBySectionId(Long sectionId);
}
