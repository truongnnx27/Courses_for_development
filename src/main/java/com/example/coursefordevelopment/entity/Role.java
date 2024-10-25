package com.example.coursefordevelopment.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name", length = 255, nullable = false)
    private String roleName;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Version
    @Column(name = "version")
    private Integer version;

}