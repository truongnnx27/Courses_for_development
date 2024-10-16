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
            @RequestParam("amount") Integer amount,
            @RequestParam("courseId") Long courseId,
            @RequestParam("userId") Long userId) {

        if (amount == null || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Amount, courseId, and userId must not be null.");
        }

        try {
            String cancelUrl = "http://localhost:8081/api/payments/vnpay/cancel";
            String successUrl = buildSuccessUrl(courseId, userId);

            // Tạo đơn hàng
            String paymentUrl = vnPayService.createOrder(amount, "Order description", successUrl);

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
                                             @RequestParam(value = "courseId") Long courseId,
                                             @RequestParam(value = "userId") Long userId) {
        // Lấy thông tin từ request
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_PayDate = request.getParameter("vnp_PayDate");

        // Ghi lại toàn bộ thông tin từ request
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        // Kiểm tra trạng thái thanh toán
        if ("00".equals(vnp_ResponseCode)) {
            CoursePayment newPayment = createNewPayment(courseId, userId, vnp_TxnRef, vnp_PayDate);
            paymentRepository.save(newPayment);
            return ResponseEntity.ok("Payment successful. Payment ID: " + newPayment.getId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment not approved. Response code: " + vnp_ResponseCode);
        }
    }

    private CoursePayment createNewPayment(Long courseId, Long userId, String txnRef, String payDate) {
        CoursePayment newPayment = new CoursePayment();
        newPayment.setAmount(BigDecimal.valueOf(0)); // Bạn có thể thay thế giá trị thực tế nếu có
        newPayment.setPaymentDate(LocalDateTime.now());
        newPayment.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        newPayment.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found")));
        newPayment.setPaymentStatus(paymentStatusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Payment status not found"))); // Giả sử 1 là ID cho trạng thái thanh toán thành công
        newPayment.setEnrollment(true);
        return newPayment;
    }

    private boolean validateSignature(String secureHash, String data) {
        // Implement logic to validate the VNPay signature here
        return true; // Replace with actual validation logic
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Payment cancelled");
    }
}