package com.example.coursefordevelopment.util;

import jakarta.servlet.http.HttpServletRequest;

public class URLUtils {

    public static String getBaseURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        // Kiểm tra nếu cổng không phải là cổng mặc định (80 cho HTTP và 443 cho HTTPS)
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);

        // Bỏ qua dấu '/' cuối cùng nếu có
        if (!url.toString().endsWith("/")) {
            url.append("/");
        }

        return url.toString();
    }
}
