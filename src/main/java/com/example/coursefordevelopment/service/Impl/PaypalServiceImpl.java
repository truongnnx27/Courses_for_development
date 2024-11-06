package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.config.PaypalPaymentIntent;
import com.example.coursefordevelopment.config.PaypalPaymentMethod;
import com.example.coursefordevelopment.dto.PaymentDto;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.repository.CourseRepository;
import com.example.coursefordevelopment.repository.PaymentRepository;
import com.example.coursefordevelopment.repository.PaymentStatusRepository;
import com.example.coursefordevelopment.repository.UserRepository;
import com.example.coursefordevelopment.service.EmailService;
import com.example.coursefordevelopment.service.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PaypalServiceImpl implements PaypalService {
    @Autowired
    private APIContext apiContext;
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
    private static final double EXCHANGE_RATE = 25000;

    @Override
    public Payment createPayment(Double total, String currency, PaypalPaymentMethod method,
                                 PaypalPaymentIntent intent, String description,
                                 String cancelUrl, String successUrl) throws PayPalRESTException {
        validatePaymentParameters(total, currency, method, intent);

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).toString());

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = Collections.singletonList(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        validateExecutionParameters(paymentId, payerId);

        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public String processPayment(Double price, Long courseId, String userId) throws PayPalRESTException {
        validateProcessPaymentParameters(price, courseId, userId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        BigDecimal amountUSD = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(EXCHANGE_RATE), 2, RoundingMode.HALF_UP);
        String cancelUrl = "http://localhost:8081/api/payments/paypal/cancel";
        String successUrl = buildSuccessUrl(courseId, userId, price);

        Payment payment = createPayment(amountUSD.doubleValue(), "USD",
                PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale,
                "Mô tả đơn hàng", cancelUrl, successUrl);

        PaymentDto paymentDto = createPaymentDto(payment, course, user, price);
        paymentRepository.save(createNewPayment(paymentDto));

        return findApprovalLink(payment)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy liên kết thanh toán."));
    }

    private void validatePaymentParameters(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent) {
        if (total == null || currency == null || method == null || intent == null) {
            throw new IllegalArgumentException("Tham số thanh toán không hợp lệ.");
        }
    }

    private void validateExecutionParameters(String paymentId, String payerId) {
        if (paymentId == null || payerId == null) {
            throw new IllegalArgumentException("ID thanh toán và ID người trả không được để trống.");
        }
    }

    private void validateProcessPaymentParameters(Double price, Long courseId, String userId) {
        if (price == null || courseId == null || userId == null) {
            throw new IllegalArgumentException("Giá, khóa học ID, và người dùng ID không được để trống.");
        }
    }

    private Optional<String> findApprovalLink(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .map(Links::getHref)
                .findFirst();
    }

    private com.example.coursefordevelopment.entity.Payment createNewPayment(PaymentDto paymentDTO) {
        com.example.coursefordevelopment.entity.Payment newPayment = new com.example.coursefordevelopment.entity.Payment();
        newPayment.setPaymentId(paymentDTO.getPaymentId());
        newPayment.setPrice(paymentDTO.getPrice());
        newPayment.setPaymentDate(paymentDTO.getPaymentDate());
        newPayment.setUser(userRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại")));
        newPayment.setCourse(courseRepository.findById(paymentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại")));
        newPayment.setPaymentStatus(paymentStatusRepository.findById(paymentDTO.getPaymentStatusId())
                .orElseThrow(() -> new RuntimeException("Trạng thái thanh toán không tồn tại")));
        newPayment.setEnrollment(paymentDTO.isEnrollment());
        return newPayment;
    }

    private PaymentDto createPaymentDto(Payment paypalPayment, Course course, User user, Double price) {
        return new PaymentDto(
                paypalPayment.getId(),
                BigDecimal.valueOf(price),
                LocalDateTime.now(),
                user.getId(),
                course.getId(),
                3L, // Trạng thái đang tiến hành
                false // Đánh dấu đã đăng ký
        );
    }

    private String buildSuccessUrl(Long courseId, String userId, Double price) {
        return String.format("http://localhost:8080/api/payments/paypal/success?courseId=%d&userId=%s&price=%.2f", courseId, userId, price);
    }

    @Override
    public void updatePaymentStatus(String paymentId, long statusId) {
        com.example.coursefordevelopment.entity.Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán cho paymentId: " + paymentId));

        // Cập nhật trạng thái thanh toán
        payment.setPaymentStatus(paymentStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Trạng thái thanh toán không tồn tại")));

        // Nếu trạng thái thanh toán là Completed
//        if (statusId == 1L) {
//            payment.setEnrollment(true); // Đặt enrollment thành true
//        } else {
//            payment.setEnrollment(false); // Đặt enrollment thành false nếu không phải Completed
//        }
        paymentRepository.save(payment);
    }
    public String getUserEmailById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getEmail).orElse(null);
    }

    @Override
    public void sendPaymentConfirmationEmail(String emailAddress, String paymentId, Double price) throws MessagingException {
        String subject = "Xác Nhận Thanh Toán";
        String body = String.format(
                "<html><body>" +
                        "<h1>Xác Nhận Thanh Toán</h1>" +
                        "<p>Thanh toán của bạn đã được xử lý thành công.</p>" +
                        "<ul>" +
                        "<li><strong>ID Thanh Toán:</strong> %s</li>" +
                        "<li><strong>Số Tiền:</strong> %.2f VND</li>" +
                        "</ul>" +
                        "</body></html>", paymentId, price
        );

        emailService.sendEmail(emailAddress, subject, body);
    }

    @Override
    public void cancelPayment(String paymentId) {
        com.example.coursefordevelopment.entity.Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán cho paymentId: " + paymentId));

        // Đặt trạng thái thanh toán là Failed (Thất bại) khi hủy
        payment.setPaymentStatus(paymentStatusRepository.findById(2L) // 2L là mã trạng thái Failed
                .orElseThrow(() -> new RuntimeException("Trạng thái thanh toán không tồn tại")));
        payment.setEnrollment(false); // Đặt enrollment thành false
        paymentRepository.save(payment);
    }
    @Scheduled(fixedRate = 60000) // Kiểm tra mỗi phút
    public void checkPendingPayments() {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        List<com.example.coursefordevelopment.entity.Payment> pendingPayments = paymentRepository.findAllByPaymentStatusId( 3L); // 3L là mã trạng thái "Pending"

        for (com.example.coursefordevelopment.entity.Payment payment : pendingPayments) {
            updatePaymentStatus(payment.getPaymentId(), 2L); // 2L là mã trạng thái "Failed"
        }
    }
}
