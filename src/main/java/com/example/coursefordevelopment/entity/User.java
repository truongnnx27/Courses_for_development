package com.example.coursefordevelopment.entity;

import com.example.coursefordevelopment.entity.Role;
import com.example.coursefordevelopment.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", length = 255)
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "username", length = 255, nullable = false)
    String username;

    @Column(name = "password", length = 255, nullable = false)
    String password;

    @Column(name = "email", columnDefinition = "TEXT", nullable = false)
    String email;

    @Column(name = "fullname", length = 255)
    String fullname;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "phone", length = 10)
    String phone;

    @Column(name = "avatar_url", length = 255)
    String avatarUrl;

    @Column(name = "updated_date")
    LocalDateTime updatedDate;

    @Column(name = "created_date")
    LocalDateTime createdDate;

    @Version
    @Column(name = "version")
    int version;

    @Column(name = "is_active", length = 255)
    private String isActive;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleEntity;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;


}
