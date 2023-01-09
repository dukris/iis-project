package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserDto;
import com.solvd.laba.iis.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.getAll().stream()
                .map(userMapper::userToUserDto)
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable long id) {
        UserDto user = userMapper.userToUserDto(userService.getById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.create(user);
        return new ResponseEntity<>(userMapper.userToUserDto(user), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.save(user);
        return new ResponseEntity<>(userMapper.userToUserDto(user), HttpStatus.OK);
    }
}
