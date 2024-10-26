package coursefordevelopment.dto.request;

import com.example.coursefordevelopment.entity.Role;
import com.example.coursefordevelopment.enums.Gender;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 3, message = "INVALID_PASSWORD")
    String password;
    String email;
    String fullname;
    Date birthday;
    Gender gender;
    String phone;
    String avatarUrl;
    LocalDateTime updatedDate;
    LocalDateTime createdDate;
    String isActive;
    Role roleEntity;
}

