package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.config.PaypalPaymentIntent;
import com.example.coursefordevelopment.config.PaypalPaymentMethod;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.PaymentRepository;
import com.example.coursefordevelopment.reponsitory.PaymentStatusRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.example.coursefordevelopment.entity.CoursePayment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
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

    @PostMapping("/pay")
    public ResponseEntity<String> pay(
            @RequestParam("amount") Double amount,
            @RequestParam("courseId") Long courseId,
            @RequestParam("userId") Long userId) {

        if (amount == null || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Amount, courseId, and userId must not be null.");
        }

        try {
            String cancelUrl = "http://localhost:8081/api/payments/paypal/cancel";
            String successUrl = buildSuccessUrl(courseId, userId);

            Payment payment = paypalService.createPayment(
                    amount, "USD", PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale, "Order description", cancelUrl, successUrl);

            return findApprovalLink(payment)
                    .map(link -> {
                        // Trả về đường dẫn thanh toán cho người dùng
                        return ResponseEntity.ok("Payment created successfully. Please complete your payment at: " + link);
                    })
                    .orElse(ResponseEntity.badRequest().body("Payment link not found."));
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment creation: " + e.getMessage());
        }
    }
    private String buildSuccessUrl(Long courseId, Long userId) {
        return String.format("http://localhost:8081/api/payments/paypal/success?courseId=%d&userId=%d", courseId, userId);
    }
    private Optional<String> findApprovalLink(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .map(Links::getHref)
                .findFirst();
    }
    @GetMapping("/success")
    public ResponseEntity<String> successPay(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            @RequestParam(value = "courseId") Long courseId,
            @RequestParam(value = "userId") Long userId) {

        try {
            Payment paypalPayment = paypalService.executePayment(paymentId, payerId);

            if ("approved".equals(paypalPayment.getState())) {
                CoursePayment newPayment = createNewPayment(paypalPayment, courseId, userId);
                paymentRepository.save(newPayment);

                return ResponseEntity.ok("Payment successful. Payment ID: " + paymentId);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment not approved");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed: " + e.getMessage());
        }
    }

    private CoursePayment createNewPayment(Payment paypalPayment, Long courseId, Long userId) {
        CoursePayment newPayment = new CoursePayment();
        newPayment.setAmount(new BigDecimal(paypalPayment.getTransactions().get(0).getAmount().getTotal()));
        newPayment.setPaymentDate(LocalDateTime.now());
        newPayment.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        newPayment.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found")));
        newPayment.setPaymentStatus(paymentStatusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Payment status not found")));
        newPayment.setEnrollment(true);
        return newPayment;
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Payment cancelled");
    }
}

