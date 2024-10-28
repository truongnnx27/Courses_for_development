<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/response/VerifyOtpResponse.java
package coursefordevelopment.dto.response;
=======
package com.example.coursefordevelopment.dto.response;
>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/dto/response/VerifyOtpResponse.java

import lombok.*;
import lombok.experimental.FieldDefaults;

<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/response/VerifyOtpResponse.java
=======
import java.time.LocalDateTime;

>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/dto/response/VerifyOtpResponse.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyOtpResponse {
    boolean valid;
}
