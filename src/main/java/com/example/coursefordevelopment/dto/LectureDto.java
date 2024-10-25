package com.example.coursefordevelopment.dto;

import com.example.coursefordevelopment.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureDto {
    private Long id;
    private String title;
    private String type;
    private List<VideoDto> videos;
    private QuizDto quiz;
}
