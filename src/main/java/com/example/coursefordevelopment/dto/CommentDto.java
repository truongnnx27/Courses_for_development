package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String commentText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;   // Chỉ map id của User để tránh lặp
    private Long lessonId; // Chỉ map id của Lesson để tránh lặp
    private Long courseId; // Chỉ map id của Course để tránh lặp
    private Long commentId; // Chỉ map id của Course để tránh lặp
    private int star;
}
