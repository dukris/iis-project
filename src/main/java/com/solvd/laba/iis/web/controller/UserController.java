package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserDto;
import com.solvd.laba.iis.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.create(user);
        return userMapper.userToUserDto(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userService.delete(user);
    }

    @PutMapping
    public UserDto update(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.save(user);
        return userMapper.userToUserDto(user);
    }
}
