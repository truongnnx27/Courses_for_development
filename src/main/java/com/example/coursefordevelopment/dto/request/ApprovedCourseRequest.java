package com.example.coursefordevelopment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedCourseRequest {
    private String courseName;
    private String email;
    private boolean courseStatus;
    private String text;
}
