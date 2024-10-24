package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.UserDto;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUserDtos() {
        return userRepository.findAll().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDto convertToUserDTO(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getProfilePicture(),
                user.getBio(),
                user.getWebsite(),
                user.getEmailVerified(),
                user.getRole().getId()
        );
    }
}
