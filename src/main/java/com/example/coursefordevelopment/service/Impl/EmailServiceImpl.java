package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.request.ApprovedCourseRequest;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.exception.AppException;
import com.example.coursefordevelopment.exception.ErrorCode;
import com.example.coursefordevelopment.repository.CourseRepository;
import com.example.coursefordevelopment.repository.UserRepository;
import com.example.coursefordevelopment.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public EmailServiceImpl(JavaMailSender mailSender, CourseRepository courseRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void sendEmailApprovedCourse(ApprovedCourseRequest approvedCourseRequest) throws MessagingException {
        Course course = courseRepository.findByCourseName(approvedCourseRequest.getCourseName());
        if (course == null) {
            throw new RuntimeException("Course not found");
        }else {
            course.setPublished(approvedCourseRequest.isCourseStatus());
            courseRepository.save(course);
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setSubject("Course status information: " + approvedCourseRequest.getCourseName());
        helper.setTo(approvedCourseRequest.getEmail());

        String htmlContent = "<html><body>" +
                "<p>Hello " + course.getInstructor().getFullname() + ",</p>" +
                "<p>Your Course: <strong>" + approvedCourseRequest.getCourseName() + "</strong> now in status: " +
                (approvedCourseRequest.isCourseStatus() ? "<span style='color:green;'>Published</span>" : "<span style='color:red;'>Draft</span>") +
                "</p>" +
                "<p>" + approvedCourseRequest.getText() + "</p>" +
                "</body></html>";

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendEmailDeleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Course deleted information: " + course.getTitle());
        message.setTo(course.getInstructor().getEmail());
        message.setText("Your course: " + course.getTitle() + " has been deleted");
        mailSender.send(message);
    }

    @Override
    public String generateOTP(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        return otp;
    }

    @Override
    public void sendOTPEmail(String email, String otp) throws MessagingException {
        if (!userRepository.existsByEmail(email))
            throw new AppException(ErrorCode.USER_EXISTED);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("OTP Confirmation");
        helper.setText("Your OTP code is: " + otp, true);
        mailSender.send(message);
    }

    @Override
    public boolean verifyOTP(String request, String encryptedOtp, LocalDateTime creationTime, LocalDateTime expirationTime) {
        return encryptedOtp != null
                && passwordEncoder.matches(request, encryptedOtp)
                && Duration.between(creationTime, expirationTime).getSeconds() <= 30;

    }
}
