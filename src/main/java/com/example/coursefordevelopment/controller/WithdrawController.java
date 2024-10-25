package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @PostMapping("/request")
    public String requestWithdraw(@RequestParam BigDecimal amount, @RequestParam String userId) {
        return withdrawService.initiateWithdraw(amount, userId);
    }

    @GetMapping("/verify")
    public String verifyWithdrawal(@RequestParam("token") String token) {
        return withdrawService.verifyWithdrawal(token);
    }
}
