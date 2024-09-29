package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {

    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long courseId;  // Map ID của Course
    private Long lessonId;  // Map ID của Lesson
}
