package com.example.coursefordevelopment.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EnvConfig {


    @Value("${S3_ACCESSKEY}")
    private String S3_ACCESSKEY;

    @Value("${S3_SECRETKEY}")
    private String S3_SECRETKEY;

    @Value("${JWT_SIGNERKEY}")
    private String JWT_SIGNERKEY;

    @Value("${SPRING_USER_EMAIL}")
    private String SPRING_USER_EMAIL;

    @Value("${SPRING_PASS_EMAIL}")
    private String SPRING_PASS_EMAIL;

    @Value("${PAYPAL_APP}")
    private String PAYPAL_APP;

    @Value("${PAYPAL_SECRET}")
    private String PAYPAL_SECRET;

    @Value("${DATA_BASE_PASS}")
    private String DATA_BASE_PASS;
}
