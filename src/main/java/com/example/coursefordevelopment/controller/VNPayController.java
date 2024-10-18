package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.entity.Payment;
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

    // Phương thức tạo đơn hàng mới
    @PostMapping("/pay")
    public ResponseEntity<String> createOrder(@RequestParam("amount") Integer amount,
                                              @RequestParam("courseId") Long courseId,
                                              @RequestParam("userId") Long userId) {
        // Kiểm tra dữ liệu đầu vào
        if (amount == null || amount <= 0 || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Amount must be positive, and courseId and userId must not be null.");
        }

        try {
            // Tạo URL thành công và URL hủy
            String successUrl = buildSuccessUrl(courseId, userId);
            String paymentUrl = vnPayService.createOrder(amount, "Payment for course ID: " + courseId, successUrl);

            // Tạo thực thể CoursePayment
            Payment newPayment = createNewPayment(amount, userId, courseId);
            newPayment.setPaymentId(vnPayService.getTransactionId()); // Lấy transactionId từ VNPay
            paymentRepository.save(newPayment); // Lưu payment vào cơ sở dữ liệu

            return ResponseEntity.ok("Payment created successfully. Please complete your payment at: " + paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during order creation: " + e.getMessage());
        }
    }

    // Tạo URL thành công cho thanh toán
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
    public ResponseEntity<String> successPay(HttpServletRequest request,
                                             @RequestParam(value = "vnp_TransactionNo") String transactionNo) {
        // Kiểm tra transactionNo
        if (transactionNo == null || transactionNo.isEmpty()) {
            return ResponseEntity.badRequest().body("Transaction number must be provided.");
        }

        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_Amount = request.getParameter("vnp_Amount");
        String txnRef = request.getParameter("vnp_TxnRef"); // Lấy txnRef từ phản hồi

        // Tìm thực thể CoursePayment dựa trên txnRef
        Payment existingPayment = paymentRepository.findByPaymentId(txnRef)
                .orElseThrow(() -> new RuntimeException("Payment not found for transactionNo: " + txnRef));

        // Cập nhật trạng thái thanh toán
        if ("00".equals(vnp_ResponseCode)) {
            updatePaymentStatus(existingPayment, vnp_Amount, 2L); // ID 2 cho trạng thái COMPLETED
            return ResponseEntity.ok("Payment successful. Payment ID: " + existingPayment.getId());
        } else {
            updatePaymentStatus(existingPayment, vnp_Amount, 3L); // ID 3 cho trạng thái FAILED
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed. Response code: " + vnp_ResponseCode);
        }
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
