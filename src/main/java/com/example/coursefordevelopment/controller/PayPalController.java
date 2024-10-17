package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.config.PaypalPaymentIntent;
import com.example.coursefordevelopment.config.PaypalPaymentMethod;
import com.example.coursefordevelopment.entity.CoursePayment;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.PaymentRepository;
import com.example.coursefordevelopment.reponsitory.PaymentStatusRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments/paypal")
public class PayPalController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private static final double EXCHANGE_RATE = 25000; // Tỷ giá VND/USD

    @PostMapping("/pay")
    public ResponseEntity<String> pay(
            @RequestParam("amount") Double amount, // Nhận số tiền là VND
            @RequestParam("courseId") Long courseId,
            @RequestParam("userId") Long userId) {

        if (amount == null || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Amount, courseId, and userId must not be null.");
        }

        try {
            // Chuyển đổi từ VND sang USD
            double amountUSD = amount / EXCHANGE_RATE;

            String cancelUrl = "http://localhost:8081/api/payments/paypal/cancel";
            String successUrl = buildSuccessUrl(courseId, userId, amount); // Thêm amount vào URL

            // Tạo một payment cho PayPal
            Payment payment = paypalService.createPayment(
                    amountUSD,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "Order description",
                    cancelUrl,
                    successUrl);

            CoursePayment newPayment = createNewPayment(payment, courseId, userId, amount);
            paymentRepository.save(newPayment); // Lưu vào cơ sở dữ liệu

            // Lưu trạng thái thanh toán là "PENDING" (ID 0)
            updatePaymentStatus(payment.getId(), 1L); // ID 0 cho PENDING

            return findApprovalLink(payment)
                    .map(link -> ResponseEntity.ok("Payment created successfully. Please complete your payment at: " + link))
                    .orElse(ResponseEntity.badRequest().body("Payment link not found."));
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment creation: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPay(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            @RequestParam(value = "courseId") Long courseId,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "amount") Double amount) {

        try {
            Payment paypalPayment = paypalService.executePayment(paymentId, payerId);

            if ("approved".equals(paypalPayment.getState())) {
                // Cập nhật trạng thái thanh toán thành COMPLETED (ID 1)
                updatePaymentStatus(paymentId, 2L); // ID 1 cho COMPLETED
                return ResponseEntity.ok("Payment successful. Payment ID: " + paymentId);
            } else {
                // Cập nhật trạng thái thanh toán thành FAILED (ID 2)
                updatePaymentStatus(paymentId, 3L); // ID 2 cho FAILED
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment not approved");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed: " + e.getMessage());
        }
    }

    private CoursePayment createNewPayment(Payment paypalPayment, Long courseId, Long userId, Double amount) {
        CoursePayment newPayment = new CoursePayment();

        // Lưu ID thanh toán từ PayPal
        newPayment.setPaymentId(paypalPayment.getId());

        // Lưu amount vào database (VND)
        newPayment.setAmount(new BigDecimal(amount)); // Lưu vào database là VND
        newPayment.setPaymentDate(LocalDateTime.now());
        newPayment.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        newPayment.setCourse(courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found")));
        newPayment.setPaymentStatus(paymentStatusRepository.findById(1L) // ID 1 cho PENDING
                .orElseThrow(() -> new RuntimeException("Payment status not found")));
        newPayment.setEnrollment(true);
        return newPayment;
    }


    private void updatePaymentStatus(String paymentId, long statusId) {
        // Tìm Payment bằng paymentId
        Optional<CoursePayment> paymentOptional = paymentRepository.findByPaymentId(paymentId);
        if (paymentOptional.isPresent()) {
            CoursePayment payment = paymentOptional.get();
            // Cập nhật trạng thái thanh toán
            payment.setPaymentStatus(paymentStatusRepository.findById(statusId)
                    .orElseThrow(() -> new RuntimeException("Payment status not found")));
            paymentRepository.save(payment); // Lưu thay đổi vào cơ sở dữ liệu
        } else {
            throw new RuntimeException("Payment not found for paymentId: " + paymentId);
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Payment cancelled");
    }

    private String buildSuccessUrl(Long courseId, Long userId, Double amount) {
        return String.format("http://localhost:8081/api/payments/paypal/success?courseId=%d&userId=%d&amount=%.2f", courseId, userId, amount);
    }

    private Optional<String> findApprovalLink(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .map(Links::getHref)
                .findFirst();
    }
}
