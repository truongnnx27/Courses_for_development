package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.request.ApprovedCourseRequest;
import com.example.coursefordevelopment.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;


    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> approvedCourse(@Valid @RequestBody ApprovedCourseRequest approvedCourseRequest) throws MessagingException {
        emailService.sendEmailApprovedCourse(approvedCourseRequest);
        return ResponseEntity.ok(Map.of("message", "Email sent to: " +approvedCourseRequest.getEmail()+" successfully"));
    }
}
