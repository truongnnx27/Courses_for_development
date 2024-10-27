package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.config.VNPayConfig;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public interface VNPayService {


    String createOrder(int total, String orderInfo, String urlReturn);

    String getTransactionId();

    int orderReturn(HttpServletRequest request);

    void successPay(HttpServletRequest request, String transactionNo) throws MessagingException;

    String cancelPay();

    Map<String, String> handlePayment(Long courseId, String userId, Integer price);
}
