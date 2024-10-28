package com.example.coursefordevelopment.service;

import jakarta.mail.MessagingException;

import java.time.LocalDateTime;

public interface EmailService {
    String generateOTP(String email);
    void sendOTPEmail(String email, String otp) throws MessagingException;
    boolean verifyOTP(String request, String encryptedOtp, LocalDateTime creationTime, LocalDateTime expirationTime);
    void sendOTPEmailForCreationUser(String email,String username, String otp) throws MessagingException;

}
