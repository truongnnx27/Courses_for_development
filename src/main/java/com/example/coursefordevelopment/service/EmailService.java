package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.request.ApprovedCourseRequest;
import jakarta.mail.MessagingException;

import java.time.LocalDateTime;

public interface EmailService {
    void sendEmailApprovedCourse(ApprovedCourseRequest approvedCourseRequest) throws MessagingException;

    void sendEmailDeleteCourse(Long id);

    String generateOTP(String email);

    void sendOTPEmail(String email, String otp) throws MessagingException;

    boolean verifyOTP(String request, String encryptedOtp, LocalDateTime creationTime, LocalDateTime expirationTime);
}
