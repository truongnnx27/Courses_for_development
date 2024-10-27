package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.service.VNPayService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/payments/vnpay")
public class VNPayController {

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> createOrder(@RequestParam("price") Integer price,
                                                           @RequestParam("courseId") Long courseId,
                                                           @RequestParam("userId") String userId) {
        return ResponseEntity.ok(vnPayService.handlePayment(courseId, userId, price));
    }

    @GetMapping("/success")
    public void successPay(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "vnp_TxnRef") String transactionNo) throws MessagingException {
        vnPayService.successPay(request, transactionNo);
        try {
            response.sendRedirect("http://localhost:8080/vue/payment-success");
        } catch (IOException e) {
            // Xử lý lỗi chuyển hướng
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok(vnPayService.cancelPay());
    }
}
