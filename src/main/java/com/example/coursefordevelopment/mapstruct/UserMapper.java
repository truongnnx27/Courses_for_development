package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.request.UserCreationRequest;
import com.example.coursefordevelopment.dto.request.UserUpdateRequest;
import com.example.coursefordevelopment.dto.response.UserResponse;
import com.example.coursefordevelopment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
