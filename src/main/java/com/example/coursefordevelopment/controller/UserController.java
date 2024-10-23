package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.UserDto;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDto convertToUserDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setBio(user.getBio());
        userDTO.setWebsite(user.getWebsite());
        userDTO.setEmailVerified(user.getEmailVerified());
        // Map roleId từ User.role.id
        userDTO.setRoleId(user.getRole().getId());

        return userDTO;
    }
}