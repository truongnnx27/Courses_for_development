package com.example.coursefordevelopment.dto;

import jakarta.validation.constraints.Size;
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
    @Size(min = 10, message = "INVALID_KEY")
    private String title;
    @Size(min = 10, message = "INVALID_CONTENT")
    private String content;
    private int duration;
    private int numberOfAttachments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lessonTypeId;  // Map ID của LessonType
    private Long sessionId;      // Map ID của Session
}
