package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.config.VNPayConfig;
import com.example.coursefordevelopment.dto.PaymentDto;
import com.example.coursefordevelopment.entity.Payment;
import com.example.coursefordevelopment.mapstruct.PaymentMapper;
import com.example.coursefordevelopment.repository.CourseRepository;
import com.example.coursefordevelopment.repository.PaymentRepository;
import com.example.coursefordevelopment.repository.PaymentStatusRepository;
import com.example.coursefordevelopment.repository.UserRepository;
import com.example.coursefordevelopment.service.EmailService;
import com.example.coursefordevelopment.service.VNPayService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VNPayServiceImpl implements VNPayService {
    private String transactionId; // Biến để lưu transaction ID
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PaymentStatusRepository paymentStatusRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public String createOrder(int total, String orderInfo, String urlReturn) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1"; // Thay thế bằng địa chỉ IP thực tế
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String orderType = "order-type";

        // Lưu mã giao dịch vào biến transactionId
        this.transactionId = vnp_TxnRef;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100)); // VNPay yêu cầu số tiền theo đơn vị VND
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locale = "vn";
        vnp_Params.put("vnp_Locale", locale);

        // URL trả về
        urlReturn += VNPayConfig.vnp_Returnurl; // Đảm bảo urlReturn là đầy đủ
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Tạo ngày giờ
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15); // Thêm 15 phút vào ngày hết hạn
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo chuỗi để tính toán chữ ký
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                // Tạo dữ liệu hash
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Tạo chuỗi truy vấn
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        return VNPayConfig.vnp_PayUrl + "?" + query.toString(); // Trả về URL thanh toán
    }

    @Override
    public String getTransactionId() {
        return transactionId; // Trả về transaction ID đã lưu
    }

    @Override
    public int orderReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                fields.put(fieldName, fieldValue);
            }
        }

        // Ghi lại các tham số nhận được từ VNPay để gỡ lỗi
        logRequestParameters(fields);

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            return -1; // Không có chữ ký
        }

        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        // Kiểm tra chữ ký
        String signValue = VNPayConfig.hashAllFields(fields);

        if (!vnp_SecureHash.equals(signValue)) {
            return -1; // Chữ ký không hợp lệ
        }

        // Lấy mã phản hồi
        String vnpResponseCode = request.getParameter("vnp_ResponseCode");
        if (vnpResponseCode == null) {
            return -1; // Không có mã phản hồi
        } else if ("00".equals(vnpResponseCode)) {
            return 1; // Thanh toán thành công
        } else {
            return 0; // Thanh toán thất bại
        }
    }

    private void logRequestParameters(Map<String, String> fields) {
        System.out.println("Request parameters from VNPay:");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public Map<String, String> handlePayment(Long courseId, String userId, Integer price) {
        // Tạo URL thành công
        String successUrl = buildSuccessUrl(courseId, userId);
        String paymentUrl = createOrder(price, "Payment for course ID: " + courseId, successUrl);

        // Tạo PaymentDTO và thực thể Payment
        PaymentDto paymentDTO = createNewPaymentDTO(price, userId, courseId);
        Payment newPayment = createNewPayment(paymentDTO);
        newPayment.setPaymentId(transactionId); // Lấy transactionId từ createOrder
        paymentRepository.save(newPayment);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
    }

    private String buildSuccessUrl(Long courseId, String userId) {
        return String.format("http://localhost:8081/api/payments/vnpay/success?courseId=%d&userId=%s", courseId, userId);
    }

    private PaymentDto createNewPaymentDTO(Integer price, String userId, Long courseId) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPrice(BigDecimal.valueOf(price));
        paymentDto.setPaymentDate(LocalDateTime.now());
        paymentDto.setUserId(userId);
        paymentDto.setCourseId(courseId);
        paymentDto.setPaymentStatusId(1L); // ID 1 cho trạng thái PENDING
        paymentDto.setEnrollment(true);
        return paymentDto;
    }

    private com.example.coursefordevelopment.entity.Payment createNewPayment(PaymentDto paymentDTO) {
        return PaymentMapper.INSTANCE.paymentDtoToPayment(paymentDTO);
    }

    @Override
    public void successPay(HttpServletRequest request, String transactionNo) throws MessagingException {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_Amount = request.getParameter("vnp_Amount");

        Payment existingPayment = paymentRepository.findByPaymentId(transactionNo)
                .orElseThrow(() -> new RuntimeException("Payment not found for transactionNo: " + transactionNo));

        if ("00".equals(vnp_ResponseCode)) {
            updatePaymentStatus(existingPayment, vnp_Amount, 2L); // ID 2 cho COMPLETED
        } else {
            updatePaymentStatus(existingPayment, vnp_Amount, 3L); // ID 3 cho FAILED
        }

        if ("00".equals(vnp_ResponseCode)) {
            String emailAddress = getUserEmailById(existingPayment.getUser().getId());
            String subject = "Payment Confirmation";
            String body = buildEmailBody(transactionNo, vnp_Amount);

            emailService.sendEmail(emailAddress, subject, body);
        }
    }

    private String buildEmailBody(String transactionNo, String vnp_Amount) {
        return "<html><head><title>Payment Confirmation</title></head><body>"
                + "<h1>Payment Confirmation</h1>"
                + "<p>Your payment has been successfully processed.</p>"
                + "<ul>"
                + "<li><strong>Transaction Number:</strong> " + transactionNo + "</li>"
                + "<li><strong>Amount:</strong> " + vnp_Amount + " VND</li>"
                + "</ul>"
                + "</body></html>";
    }

    private String getUserEmailById(String userId) {
        return userRepository.findEmailById(userId);
    }

    private void updatePaymentStatus(Payment payment, String vnp_Amount, Long statusId) {
        payment.setPaymentStatus(paymentStatusRepository.findById(statusId).orElseThrow(() -> new RuntimeException("Payment status not found")));
        payment.setPrice(BigDecimal.valueOf(Long.parseLong(vnp_Amount)));
        paymentRepository.save(payment);
    }

    @Override
    public String cancelPay() {
        return "Payment was canceled.";
    }
}



