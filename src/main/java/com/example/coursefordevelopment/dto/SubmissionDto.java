package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {
    private Long id;
    private Long assignmentId;  // Map ID của Assignment
    private Long userId;        // Map ID của User
    private String submissionFileUrl;
    private LocalDateTime submittedAt;
    private BigDecimal grade;
}
