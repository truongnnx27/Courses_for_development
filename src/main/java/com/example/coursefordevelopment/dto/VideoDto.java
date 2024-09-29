package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private Long id;
    private Long userId;           // Map ID của User
    private Long lessonId;         // Map ID của Lesson
    private String videoUrl;
    private boolean isCompleted;
}
