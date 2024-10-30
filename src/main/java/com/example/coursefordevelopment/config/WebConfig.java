package com.example.coursefordevelopment.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**") // Áp dụng CORS cho endpoint WebSocket
                .allowedOrigins("http://localhost:8081") // Cho phép nguồn cụ thể
                .allowCredentials(true) // Cho phép gửi thông tin xác thực
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Các phương thức được phép
    }
}
