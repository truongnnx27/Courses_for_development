package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.LectureDto;
import com.example.coursefordevelopment.entity.Lecture;
import com.example.coursefordevelopment.mapstruct.LectureMapper;
import com.example.coursefordevelopment.repository.LectureRepository;
import com.example.coursefordevelopment.service.LectureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper = LectureMapper.INSTANCE;

    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public LectureDto getLessonById(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new RuntimeException("Lecture not found"));
        return lectureMapper.lectureToLectureDto(lecture);
    }

    @Override
    public List<LectureDto> getAllLessons() {
        return lectureRepository.findAll().stream()
                .map(lectureMapper::lectureToLectureDto)
                .collect(Collectors.toList());
    }

    @Override
    public LectureDto createLesson(LectureDto lectureDto) {
        Lecture lecture = lectureMapper.lectureDtoToLecture(lectureDto);
        lecture.setSection(lectureMapper.lectureDtoToLecture(lectureDto).getSection());
        lecture.setVideos(lectureMapper.lectureDtoToLecture(lectureDto).getVideos());
        lecture.setQuiz(lectureMapper.lectureDtoToLecture(lectureDto).getQuiz());
        lecture = lectureRepository.save(lecture);
        return lectureMapper.lectureToLectureDto(lecture);
    }

    @Override
    public LectureDto updateLesson(Long id, LectureDto lectureDto) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new RuntimeException("Lecture not found"));
        lecture.setTitle(lectureDto.getTitle());
        lecture = lectureRepository.save(lecture);
        return lectureMapper.lectureToLectureDto(lecture);
    }

    @Override
    public void deleteLesson(Long id) {
        lectureRepository.deleteById(id);
    }
}
