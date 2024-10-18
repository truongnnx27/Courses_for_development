package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.config.PaypalPaymentIntent;
import com.example.coursefordevelopment.config.PaypalPaymentMethod;
import com.example.coursefordevelopment.entity.Payment;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.PaymentRepository;
import com.example.coursefordevelopment.reponsitory.PaymentStatusRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.PaypalService;
import com.paypal.api.payments.Links;
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

    private static final double EXCHANGE_RATE = 25000;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestParam("amount") Double amount,
                                      @RequestParam("courseId") Long courseId,
                                      @RequestParam("userId") Long userId) {
        // Kiểm tra xem các tham số đầu vào có hợp lệ hay không
        if (amount == null || courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Amount, courseId, and userId must not be null.");
        }

        try {
            // Chuyển đổi số tiền từ VND sang USD
            double amountUSD = amount / EXCHANGE_RATE;
            String cancelUrl = "http://localhost:8081/api/payments/paypal/cancel"; // URL hủy thanh toán
            String successUrl = buildSuccessUrl(courseId, userId, amount); // Tạo URL thành công

            // Tạo một đối tượng Payment mới trên PayPal
            com.paypal.api.payments.Payment payment = paypalService.createPayment(amountUSD, "USD",
                    PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale,
                    "Order description", cancelUrl, successUrl);

            // Tạo một đối tượng CoursePayment mới và lưu vào cơ sở dữ liệu
            Payment newPayment = createNewPayment(payment, courseId, userId, amount);
            paymentRepository.save(newPayment);

            updatePaymentStatus(payment.getId(), 1L); // ID 1 cho PENDING

            // Tìm link phê duyệt và trả về phản hồi
            return findApprovalLink(payment)
                    .map(link -> ResponseEntity.ok("Payment created successfully. Please complete your payment at: " + link))
                    .orElse(ResponseEntity.badRequest().body("Payment link not found."));
        } catch (PayPalRESTException e) {
            // Xử lý lỗi nếu có vấn đề xảy ra trong quá trình tạo thanh toán
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment creation: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId,
                                             @RequestParam("PayerID") String payerId,
                                             @RequestParam(value = "courseId") Long courseId,
                                             @RequestParam(value = "userId") Long userId,
                                             @RequestParam(value = "amount") Double amount) {
        try {
            // Thực hiện thanh toán và nhận thông tin trạng thái từ PayPal
            com.paypal.api.payments.Payment paypalPayment = paypalService.executePayment(paymentId, payerId);
            Long statusId = "approved".equals(paypalPayment.getState()) ? 2L : 3L; // ID 2 cho COMPLETED, ID 3 cho FAILED

            // Cập nhật trạng thái thanh toán
            updatePaymentStatus(paymentId, statusId);
            return ResponseEntity.ok("Payment " + (statusId == 2L ? "successful" : "not approved") + ". Payment ID: " + paymentId);
        } catch (PayPalRESTException e) {
            // Xử lý lỗi nếu có vấn đề xảy ra trong quá trình thực hiện thanh toán
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed: " + e.getMessage());
        }
    }

    private Payment createNewPayment(com.paypal.api.payments.Payment paypalPayment, Long courseId, Long userId, Double amount) {
        Payment newPayment = new Payment();
        newPayment.setPaymentId(paypalPayment.getId()); // Lưu ID thanh toán từ PayPal
        newPayment.setAmount(new BigDecimal(amount)); // Lưu số tiền vào database (VND)
        newPayment.setPaymentDate(LocalDateTime.now()); // Lưu thời gian thanh toán hiện tại
        newPayment.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"))); // Lấy thông tin người dùng
        newPayment.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"))); // Lấy thông tin khóa học
        newPayment.setPaymentStatus(paymentStatusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Payment status not found"))); // ID 1 cho PENDING
        newPayment.setEnrollment(true); // Đánh dấu đăng ký thành công
        return newPayment; // Trả về đối tượng CoursePayment mới
    }

    private void updatePaymentStatus(String paymentId, long statusId) {
        // Tìm thanh toán dựa trên paymentId
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found for paymentId: " + paymentId));
        // Cập nhật trạng thái thanh toán
        payment.setPaymentStatus(paymentStatusRepository.findById(statusId).orElseThrow(() -> new RuntimeException("Payment status not found")));
        paymentRepository.save(payment); // Lưu thay đổi vào cơ sở dữ liệu
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPay() {
        // Xử lý khi thanh toán bị hủy
        return ResponseEntity.ok("Payment cancelled");
    }

    private String buildSuccessUrl(Long courseId, Long userId, Double amount) {
        // Tạo URL thành công với các tham số cần thiết
        return String.format("http://localhost:8081/api/payments/paypal/success?courseId=%d&userId=%d&amount=%.2f", courseId, userId, amount);
    }

    private Optional<String> findApprovalLink(com.paypal.api.payments.Payment payment) {
        // Tìm link phê duyệt trong danh sách các link trả về từ PayPal
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel())) // Lọc link có rel là "approval_url"
                .map(Links::getHref) // Lấy href của link
                .findFirst(); // Trả về link đầu tiên tìm được
    }
}
