<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/WishlistDto.java
package coursefordevelopment.dto;
=======
package com.example.coursefordevelopment.dto;
>>>>>>> truongdev:src/main/java/com/example/coursefordevelopment/dto/WishlistDto.java

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {

    Long id;
    Long userId;
    Long courseId;
    LocalDateTime addAt;
}
