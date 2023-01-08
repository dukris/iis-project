package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserDto;
import com.solvd.laba.iis.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAll() {
        List<UserDto> users = userService.getAll().stream()
                .map(userMapper::userToUserDto)
                .toList();
        return users;
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        UserDto user = userMapper.userToUserDto(userService.getById(id));
        return user;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.create(user);
        return userMapper.userToUserDto(user);
    }

    @DeleteMapping
    public void delete(@RequestBody UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userService.delete(user);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.save(user);
        return userMapper.userToUserDto(user);
    }
}
