package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
