package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.config.PaypalPaymentIntent;
import com.example.coursefordevelopment.config.PaypalPaymentMethod;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public interface PaypalService {


    Payment createPayment(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException, MessagingException;

    String processPayment(Double price, Long courseId, String userId) throws PayPalRESTException;

    String getUserEmailById(String userId);

    void updatePaymentStatus(String paymentId, long statusId);

    void sendPaymentConfirmationEmail(String emailAddress, String paymentId, Double price) throws MessagingException;

    void cancelPayment(String paymentId);
}
