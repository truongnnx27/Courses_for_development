package com.example.coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.request.UserCreationRequest;
import com.example.coursefordevelopment.dto.request.UserUpdateRequest;
import com.example.coursefordevelopment.dto.response.TopIntructorResponse;
import com.example.coursefordevelopment.dto.response.UserResponse;
import com.example.coursefordevelopment.entity.Role;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.exception.AppException;
import com.example.coursefordevelopment.exception.ErrorCode;
import com.example.coursefordevelopment.mapstruct.UserMapper;
import com.example.coursefordevelopment.reponsitory.RoleRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.ImageService;
import com.example.coursefordevelopment.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Override
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user =  userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setRoleEntity(defaultRole);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    @Override
    public List<TopIntructorResponse> topIntructor() {
        List<Object[]> results = userRepository.findTopIntructor();
        List<TopIntructorResponse> topIntructorResponses = results.stream()
                .map(result -> new TopIntructorResponse(
                        (String) result[0],
                        (String) result[1],
                        (String) result[2],
                        (Long) result[3]
                )).collect(Collectors.toList());
        topIntructorResponses.forEach(topIntructorResponse -> {
            try {
                topIntructorResponse.setAvatarUrl(imageService.base64Image(topIntructorResponse.getAvatarUrl(), "src/main/resources/static/avatars/"));
            } catch (IOException e) {
                topIntructorResponse.setAvatarUrl("");
            }
        });
        return topIntructorResponses;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getUsers(){
        log.info("Get all users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id){
        log.info("Get user with id {}", id);
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }


}
