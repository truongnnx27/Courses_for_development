package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.service.PaypalPayoutService;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payouts")
public class PaypalPayoutController {

    @Autowired
    private PaypalPayoutService paypalPayoutService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayout(
            @RequestParam String receiverUserId,
            @RequestParam Double amount,
            @RequestParam String note) {
        try {
            PayoutBatch payoutBatch = paypalPayoutService.createPayout(receiverUserId, amount, note);
            return ResponseEntity.ok(payoutBatch);
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
