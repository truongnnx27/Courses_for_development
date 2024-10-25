package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.service.EmailService;
import com.example.coursefordevelopment.service.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/payments/paypal")
public class PayPalController {

    @Autowired
    private PaypalService paypalService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestParam("price") Double price,
                                      @RequestParam("courseId") Long courseId,
                                      @RequestParam("userId") String userId) {
        try {
            String paymentUrl = paypalService.processPayment(price, courseId, userId);
            return ResponseEntity.ok("{\"paymentUrl\":\"" + paymentUrl + "\"}");
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment creation: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/success")
    public void successPay(HttpServletResponse httpResponse,
                           @RequestParam("paymentId") String paymentId,
                           @RequestParam("PayerID") String payerId,
                           @RequestParam(value = "courseId") Long courseId,
                           @RequestParam(value = "userId") String userId,
                           @RequestParam(value = "price") Double price) {
        try {
            Payment paypalPayment = paypalService.executePayment(paymentId, payerId);
            Long statusId = "approved".equals(paypalPayment.getState()) ? 2L : 3L;

            paypalService.updatePaymentStatus(paymentId, statusId);


            if (statusId.equals(2L)) {
                String email = paypalService.getUserEmailById(userId); // Lấy email người dùng
                if (email != null) {
                    paypalService.sendPaymentConfirmationEmail(email, paymentId, price); // Gửi email xác nhận
                }
            }

            httpResponse.sendRedirect("http://localhost:8080/vue/payment-success");
        } catch (PayPalRESTException e) {
            // Xử lý lỗi
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        return ResponseEntity.ok("Payment cancelled");
    }


}
