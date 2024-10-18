package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.config.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    private String transactionId; // Biến để lưu transaction ID

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

    public String getTransactionId() {
        return transactionId; // Trả về transaction ID đã lưu
    }

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
}
