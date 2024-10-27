package coursefordevelopment.dto.request;

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