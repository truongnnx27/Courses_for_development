<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/response/UserResponse.java
package coursefordevelopment.dto.response;
=======
package com.example.coursefordevelopment.dto.response;
>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/dto/response/UserResponse.java

import com.example.coursefordevelopment.entity.Role;
import com.example.coursefordevelopment.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String fullname;
    Date birthday;
    Gender gender;
    String phone;
    String avatarUrl;
    LocalDateTime updatedDate;
    LocalDateTime createdDate;
    int version;
<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/response/UserResponse.java
    String isActive;
=======
    boolean isActive;
>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/dto/response/UserResponse.java
    Role roleEntity;
}
