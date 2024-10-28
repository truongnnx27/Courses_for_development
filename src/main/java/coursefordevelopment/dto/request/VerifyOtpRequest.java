<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/request/VerifyOtpRequest.java
package coursefordevelopment.dto.request;
=======
package com.example.coursefordevelopment.dto.request;
>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/dto/request/VerifyOtpRequest.java

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyOtpRequest {
    LocalDateTime creationTime;
    String otp;
    String hashedOtp;
}