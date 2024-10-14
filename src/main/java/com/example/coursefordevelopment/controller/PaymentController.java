package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.entity.Payment;
import com.example.coursefordevelopment.entity.PaymentStatus;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.dto.PaymentDto;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.PaymentRepository;
import com.example.coursefordevelopment.reponsitory.PaymentStatusRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final APIContext apiContext;

    @PostMapping("/paypal")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDto paymentDto) {
        try {
            // Lấy thông tin User và Course từ DB
            User user = userRepository.findById(paymentDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Course course = courseRepository.findById(paymentDto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            // Tạo PayPal payment object
            Payment payment = new Payment();
            payment.setIntent("sale");
            // Thiết lập các thông tin cần thiết khác cho PayPal payment
            payment.setPayer(paymentDto.getPayer());  // Tùy thuộc vào cấu hình của bạn
            payment.setTransactions(paymentDto.getTransactions());  // Tùy chỉnh thêm các transaction details

            // Execute payment with PayPal API
            Payment createdPayment = payment.create(apiContext);

            // Tạo một bản ghi Payment trong database
            Payment newPayment = new Payment();
            newPayment.setUser(user);
            newPayment.setCourse(course);
            newPayment.setAmount(new BigDecimal(createdPayment.getTransactions().get(0).getAmount().getTotal()));
            newPayment.setPaymentDate(LocalDateTime.now());

            // Cập nhật trạng thái thanh toán
            PaymentStatus paymentStatus = paymentStatusRepository.findByStatusName("Completed")
                    .orElseThrow(() -> new RuntimeException("Payment Status not found"));
            newPayment.setPaymentStatus(paymentStatus);
            newPayment.setEnrollment(true);

            // Lưu payment vào database
            paymentRepository.save(newPayment);

            return ResponseEntity.ok("Payment successful and saved.");
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Payment failed");
        }
    }
}
