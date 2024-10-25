package com.example.coursefordevelopment.controller;//package edu.learning.cfd.controller;
//import edu.learning.cfd.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class OtpController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @GetMapping("/send-otp")
//    public String sendOtp(@RequestParam String email) {
//        emailService.sendOtpEmail(email);
//
//        return "OTP đã được gửi đến email " + email;
//    }
//
//    @PostMapping("/verify-otp")
//    public String verifyOtp(@RequestParam String otp) {
//        boolean isValid = emailService.verifyOtp(otp);
//        if (isValid) {
//            return "Mã OTP chính xác!";
//        } else {
//            return "Mã OTP không hợp lệ!";
//        }
//    }
//}
