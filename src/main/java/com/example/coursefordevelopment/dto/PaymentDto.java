package com.example.coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long courseId;  // Map chỉ ID của Course
    private Long userId;    // Map chỉ ID của User
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private Boolean enrollment;
    private Long paymentStatusId; // Map ID của PaymentStatus
}
