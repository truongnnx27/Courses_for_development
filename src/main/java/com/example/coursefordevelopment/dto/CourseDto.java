package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private Long category;
    private BigDecimal rating;
    private int numberOfRatings;
    private int numberOfStudents;
    private BigDecimal price;
    private String language;
    private Long userId; // Map user chỉ qua id để tránh lặp vô hạn
    private Long courseLevelId; // Map courseLevel qua id
}
