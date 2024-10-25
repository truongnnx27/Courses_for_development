package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.entity.Payment;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.PaymentRepository;
import com.example.coursefordevelopment.reponsitory.PaymentStatusRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.EmailService;
import com.example.coursefordevelopment.service.VNPayService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private EmailService emailService;

    // Phương thức tạo đơn hàng mới
    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> createOrder(@RequestParam("amount") Integer amount,
                                                           @RequestParam("courseId") Long courseId,
                                                           @RequestParam("userId") Long userId) {
        // Kiểm tra dữ liệu đầu vào
        if (amount == null || amount <= 0 || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body(Map.of("paymentUrl", ""));
        }

        try {
            // Tạo URL thành công và URL hủy
            String successUrl = buildSuccessUrl(courseId, userId);
            String paymentUrl = vnPayService.createOrder(amount, "Payment for course ID: " + courseId, successUrl);

            // Tạo thực thể CoursePayment
            Payment newPayment = createNewPayment(amount, userId, courseId);
            newPayment.setPaymentId(vnPayService.getTransactionId());
            paymentRepository.save(newPayment);

            // Trả về paymentUrl trong response
            Map<String, String> response = new HashMap<>();
            response.put("paymentUrl", paymentUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error during order creation: " + e.getMessage()));
        }
    }

    private String buildSuccessUrl(Long courseId, Long userId) {
        return String.format("http://localhost:8081/api/payments/vnpay/success?courseId=%d&userId=%d", courseId, userId);
    }
    // Tạo thực thể CoursePayment mới
    private Payment createNewPayment(Integer amount, Long userId, Long courseId) {
        Payment newPayment = new Payment();
        newPayment.setAmount(BigDecimal.valueOf(amount)); // Gán số tiền
        newPayment.setPaymentDate(LocalDateTime.now()); // Gán thời gian thanh toán
        newPayment.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"))); // Tìm người dùng
        newPayment.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"))); // Tìm khóa học
        newPayment.setPaymentStatus(paymentStatusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Payment status not found"))); // ID 1 cho trạng thái PENDING
        newPayment.setEnrollment(true); // Đánh dấu đã đăng ký
        return newPayment;
    }

    // Phương thức xử lý phản hồi thành công từ VNPay
    @GetMapping("/success")
    public void successPay(HttpServletRequest request, HttpServletResponse httpResponse,
                           @RequestParam(value = "vnp_TxnRef") String transactionNo) {
        try {
            // Kiểm tra transactionNo
            if (transactionNo == null || transactionNo.isEmpty()) {
                throw new IllegalArgumentException("Transaction number must be provided.");
            }

            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String vnp_Amount = request.getParameter("vnp_Amount");

            // Tìm payment dựa trên txnRef
            Payment existingPayment = paymentRepository.findByPaymentId(transactionNo)
                    .orElseThrow(() -> new RuntimeException("Payment not found for transactionNo: " + transactionNo));

            // Cập nhật trạng thái thanh toán
            if ("00".equals(vnp_ResponseCode)) {
                updatePaymentStatus(existingPayment, vnp_Amount, 2L); // ID 2 cho COMPLETED
            } else {
                updatePaymentStatus(existingPayment, vnp_Amount, 3L); // ID 3 cho FAILED
            }

            // Nếu thanh toán thành công, gửi email xác nhận (nếu cần)
            if ("00".equals(vnp_ResponseCode)) {
                String emailAddress = getUserEmailById(existingPayment.getUserId()); // Lấy email của người dùng
                String subject = "Payment Confirmation";
                StringBuilder body = new StringBuilder();
                body.append("<html>")
                        .append("<head><title>Payment Confirmation</title></head>")
                        .append("<body>")
                        .append("<h1>Payment Confirmation</h1>")
                        .append("<p>Your payment has been successfully processed.</p>")
                        .append("<ul>")
                        .append("<li><strong>Transaction Number:</strong> ").append(transactionNo).append("</li>")
                        .append("<li><strong>Amount:</strong> ").append(vnp_Amount).append(" VND</li>")
                        .append("</ul>")
                        .append("</body>")
                        .append("</html>");

                emailService.sendEmail(emailAddress, subject, body.toString());
            }

            // Chuyển hướng đến trang thành công
            String redirectUrl = "http://localhost:8080/vue/payment-success"; // Thay URL chính xác
            httpResponse.sendRedirect(redirectUrl);

        } catch (IllegalArgumentException | MessagingException e) {
            // Xử lý lỗi khi không có transactionNo
            System.err.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            // Xử lý lỗi khi không tìm thấy payment
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            // Xử lý lỗi khi chuyển hướng
            System.err.println("Error: " + e.getMessage());
        }
    }
    private String getUserEmailById(Long userId) {

        return userRepository.findEmailById(userId);
    }

    // Phương thức cập nhật trạng thái thanh toán
    private void updatePaymentStatus(Payment payment, String vnp_Amount, Long statusId) {
        payment.setPaymentStatus(paymentStatusRepository.findById(statusId).orElseThrow(() -> new RuntimeException("Payment status not found")));
        payment.setAmount(BigDecimal.valueOf(Long.parseLong(vnp_Amount))); // Gán số tiền từ phản hồi
        paymentRepository.save(payment); // Lưu payment đã cập nhật
    }

    // Phương thức xử lý khi thanh toán bị hủy
    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Payment was canceled.");
    }
}
