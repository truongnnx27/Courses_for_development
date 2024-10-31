package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.UserDto;
import com.example.coursefordevelopment.dto.request.UserCreationRequest;
import com.example.coursefordevelopment.dto.request.UserUpdateRequest;
import com.example.coursefordevelopment.dto.response.TopIntructorResponse;
import com.example.coursefordevelopment.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    UserResponse getUser(String id);

    UserResponse updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);

    List<TopIntructorResponse> topIntructor();
}
