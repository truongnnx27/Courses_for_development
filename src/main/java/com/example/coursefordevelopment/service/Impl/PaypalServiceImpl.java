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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PaypalServiceImpl implements PaypalService {

    private final APIContext apiContext;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private static final double EXCHANGE_RATE = 25000;

    // Constructor to initialize APIContext and repositories
    public PaypalServiceImpl(APIContext apiContext, PaymentRepository paymentRepository,
                             PaymentStatusRepository paymentStatusRepository,
                             CourseRepository courseRepository,
                             UserRepository userRepository, EmailService emailService) {
        this.apiContext = apiContext;
        this.paymentRepository = paymentRepository;
        this.paymentStatusRepository = paymentStatusRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

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
                .orElseThrow(() -> new RuntimeException("Course not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal amountUSD = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(EXCHANGE_RATE), 2, RoundingMode.HALF_UP);
        String cancelUrl = "http://localhost:8081/api/payments/paypal/cancel";
        String successUrl = buildSuccessUrl(courseId, userId, price);

        Payment payment = createPayment(amountUSD.doubleValue(), "USD",
                PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale,
                "Order description", cancelUrl, successUrl);

        PaymentDto paymentDto = createPaymentDto(payment, course, user, price);
        paymentRepository.save(createNewPayment(paymentDto));

        return findApprovalLink(payment)
                .orElseThrow(() -> new RuntimeException("Payment link not found."));
    }

    private void validatePaymentParameters(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent) {
        if (total == null || currency == null || method == null || intent == null) {
            throw new IllegalArgumentException("Invalid payment parameters.");
        }
    }

    private void validateExecutionParameters(String paymentId, String payerId) {
        if (paymentId == null || payerId == null) {
            throw new IllegalArgumentException("Payment ID and Payer ID cannot be null.");
        }
    }

    private void validateProcessPaymentParameters(Double price, Long courseId, String userId) {
        if (price == null || courseId == null || userId == null) {
            throw new IllegalArgumentException("Price, courseId, and userId must not be null.");
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
        newPayment.setUser(userRepository.findById(paymentDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        newPayment.setCourse(courseRepository.findById(paymentDTO.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found")));
        newPayment.setPaymentStatus(paymentStatusRepository.findById(paymentDTO.getPaymentStatusId()).orElseThrow(() -> new RuntimeException("Payment status not found")));
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
                true // Đánh dấu đã đăng ký
        );
    }

    private String buildSuccessUrl(Long courseId, String userId, Double price) {
        return String.format("http://localhost:8081/api/payments/paypal/success?courseId=%d&userId=%s&price=%.2f", courseId, userId, price);
    }

    @Override
    public void updatePaymentStatus(String paymentId, long statusId) {
        com.example.coursefordevelopment.entity.Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found for paymentId: " + paymentId));
        payment.setPaymentStatus(paymentStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Payment status not found")));
        paymentRepository.save(payment);
    }

    public String getUserEmailById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getEmail).orElse(null);
    }

    @Override
    public void sendPaymentConfirmationEmail(String emailAddress, String paymentId, Double price) throws MessagingException {
        String subject = "Payment Confirmation";
        String body = String.format(
                "<html><body>" +
                        "<h1>Payment Confirmation</h1>" +
                        "<p>Your payment has been successfully processed.</p>" +
                        "<ul>" +
                        "<li><strong>Payment ID:</strong> %s</li>" +
                        "<li><strong>Amount:</strong> %.2f VND</li>" +
                        "</ul>" +
                        "</body></html>", paymentId, price
        );

        emailService.sendEmail(emailAddress, subject, body);
    }
}
