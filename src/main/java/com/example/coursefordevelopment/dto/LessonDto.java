package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String content;
    private int duration;
    private int numberOfAttachments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lessonTypeId;  // Map ID của LessonType
    private List<Long> quizIds;  // Map ID của các Quiz
    private Long sessionId;      // Map ID của Session
}
