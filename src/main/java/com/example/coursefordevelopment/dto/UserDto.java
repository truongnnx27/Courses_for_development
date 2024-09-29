package com.example.coursefordevelopment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String profilePicture;
    private String bio;
    private String website;
    private Boolean emailVerified;
    private Long roleId; // Map role chỉ qua id để tránh lặp vô hạn
}
