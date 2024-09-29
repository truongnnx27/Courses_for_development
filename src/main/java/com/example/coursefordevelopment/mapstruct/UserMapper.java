package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.UserDto;
import com.example.coursefordevelopment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role.id", target = "roleId")
    UserDto userToUserDto(User user);

    @Mapping(source = "roleId", target = "role.id")
    User userDtoToUser(UserDto userDto);
}
