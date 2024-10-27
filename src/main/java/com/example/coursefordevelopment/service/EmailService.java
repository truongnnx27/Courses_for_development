package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.request.ApprovedCourseRequest;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailApprovedCourse(ApprovedCourseRequest approvedCourseRequest) throws MessagingException;

    void sendEmailDeleteCourse(Long id);
}
