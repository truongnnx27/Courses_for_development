package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.entity.Withdraw;
import com.example.coursefordevelopment.repository.PaymentRepository;
import com.example.coursefordevelopment.repository.UserRepository;
import com.example.coursefordevelopment.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WithdrawService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private PaymentRepository paymentRepository; // Khai báo PaymentRepository

    @Autowired
    private EmailService emailService;

    public String initiateWithdraw(BigDecimal amount, String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return "User not found!";
        }

        // Tính toán số dư từ các giao dịch thanh toán
        BigDecimal totalPayments = paymentRepository.getTotalPaymentsByUserId(userId);

        // Kiểm tra số dư
        if (totalPayments == null || totalPayments.compareTo(amount) < 0) {
            return "Insufficient balance!";
        }

        // Tạo mã xác minh (token)
        String verificationToken = UUID.randomUUID().toString();

        // Lưu yêu cầu rút tiền vào bảng Withdraw
        Withdraw withdraw = new Withdraw();
        withdraw.setUser(user);
        withdraw.setAmount(amount);
        withdraw.setRequestDate(LocalDateTime.now());
        withdraw.setToken(verificationToken);
        withdrawRepository.save(withdraw);

        // Tạo liên kết xác minh
        String verificationLink = "http://localhost:8081/api/withdraw/verify?token=" + verificationToken;

        // Nội dung email xác thực
        String emailContent = "<h1>Withdrawal Verification</h1>" +
                "<p>Click the following link to confirm your withdrawal request:</p>" +
                "<a href=\"" + verificationLink + "\">Confirm Withdrawal</a>";

//        try {
//            emailService.sendEmail(user.getEmail(), "Withdrawal Verification", emailContent);
//        } catch (MessagingException e) {
//            return "Failed to send verification email.";
//        }

        return "A verification email has been sent to your email address.";
    }

    public String verifyWithdrawal(String token) {
        Withdraw withdraw = withdrawRepository.findByToken(token);

        if (withdraw == null) {
            return "Invalid or expired verification token!";
        }

        // Tính toán số dư từ các giao dịch thanh toán
        BigDecimal totalPayments = paymentRepository.getTotalPaymentsByUserId(withdraw.getUser().getId());

        // Kiểm tra số dư và cập nhật nếu mã hợp lệ
        if (totalPayments == null || totalPayments.compareTo(withdraw.getAmount()) < 0) {
            return "Insufficient balance!";
        }

        // Cập nhật trạng thái yêu cầu
        withdrawRepository.delete(withdraw);

        return "Withdrawal successful!";
    }
}