package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private Long id;
    private Long courseId;         // Map ID của Course
    private String nameSessions;
    private List<Long> lessonIds;   // Map ID của các Lesson
}
