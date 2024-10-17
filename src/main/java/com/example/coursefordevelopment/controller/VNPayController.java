package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.entity.CoursePayment;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.PaymentRepository;
import com.example.coursefordevelopment.reponsitory.PaymentStatusRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Enumeration;

@RestController
@RequestMapping("/api/payments/vnpay")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/pay")
    public ResponseEntity<String> createOrder(
            @RequestParam("amount") Integer amount, // This is in VND
            @RequestParam("courseId") Long courseId,
            @RequestParam("userId") Long userId) {

        if (amount == null || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Amount, courseId, and userId must not be null.");
        }

        try {
            String cancelUrl = "http://localhost:8081/api/payments/vnpay/cancel";
            String successUrl = buildSuccessUrl(courseId, userId);

            // Create VNPay order
            String paymentUrl = vnPayService.createOrder(amount, successUrl, cancelUrl);

            // Create new payment record in the database with PENDING status
            CoursePayment newPayment = new CoursePayment();
            newPayment.setAmount(BigDecimal.valueOf(amount)); // Save amount
            newPayment.setPaymentDate(LocalDateTime.now());
            newPayment.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")));
            newPayment.setCourse(courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found")));
            newPayment.setPaymentStatus(paymentStatusRepository.findById(1L) // ID 1 for PENDING
                    .orElseThrow(() -> new RuntimeException("Payment status not found")));
            newPayment.setEnrollment(true);
            paymentRepository.save(newPayment); // Save to database

            return ResponseEntity.ok("Payment created successfully. Please complete your payment at: " + paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during order creation: " + e.getMessage());
        }
    }

    private String buildSuccessUrl(Long courseId, Long userId) {
        return String.format("http://localhost:8081/api/payments/vnpay/success?courseId=%d&userId=%d", courseId, userId);
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPay(HttpServletRequest request,
                                             @RequestParam(value = "vnp_TransactionNo") String transactionNo,
                                             @RequestParam(value = "courseId") Long courseId,
                                             @RequestParam(value = "userId") Long userId) {
        // Check transactionNo
        if (transactionNo == null) {
            return ResponseEntity.badRequest().body("Transaction number must be provided.");
        }

        // Log transactionNo for debugging
        System.out.println("Received transaction number: " + transactionNo);

        // Retrieve parameters from request
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_Amount = request.getParameter("vnp_Amount");

        // Log all parameters from request for debugging
        logRequestParameters(request);

        // Find payment transaction by transactionNo
        CoursePayment existingPayment = paymentRepository.findByPaymentId(transactionNo)
                .orElseThrow(() -> new RuntimeException("Payment not found for transactionNo: " + transactionNo));

        // Log the retrieved payment for verification
        System.out.println("Found payment: " + existingPayment);

        // Check payment status
        if ("00".equals(vnp_ResponseCode)) {
            BigDecimal amountInVND = BigDecimal.valueOf(Long.parseLong(vnp_Amount.trim()));
            existingPayment.setPaymentDate(LocalDateTime.now());
            existingPayment.setAmount(amountInVND); // Update amount
            existingPayment.setPaymentStatus(paymentStatusRepository.findById(2L) // ID 2 for COMPLETED
                    .orElseThrow(() -> new RuntimeException("Payment status not found")));
            paymentRepository.save(existingPayment); // Save changes to database
            return ResponseEntity.ok("Payment successful. Payment ID: " + existingPayment.getId());
        } else {
            // Update payment status to FAILED
            existingPayment.setPaymentStatus(paymentStatusRepository.findById(3L) // ID 3 for FAILED
                    .orElseThrow(() -> new RuntimeException("Payment status not found")));
            paymentRepository.save(existingPayment); // Save changes to database
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment not approved. Response code: " + vnp_ResponseCode);
        }
    }
    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Payment cancelled");
    }

    @GetMapping("/return")
    public ResponseEntity<String> returnPay() {
        return ResponseEntity.ok("Payment return success.");
    }

    private void logRequestParameters(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }
    }
}
