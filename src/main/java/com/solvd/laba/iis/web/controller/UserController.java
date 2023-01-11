package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import com.solvd.laba.iis.web.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserInfoMapper userInfoMapper;

    @GetMapping
    public List<UserDto> getAll() {
        List<UserDto> users = userInfoMapper.listToListDto(userService.getAll());
        return users;
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        UserDto user = userInfoMapper.userToUserDto(userService.getById(id));
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Validated(OnCreateGroup.class) UserDto userDto) {
        UserInfo userInfo = userInfoMapper.userDtoToUser(userDto);
        userInfo = userService.create(userInfo);
        return userInfoMapper.userToUserDto(userInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) UserDto userDto) {
        UserInfo userInfo = userInfoMapper.userDtoToUser(userDto);
        userService.delete(userInfo);
    }

    @PutMapping
    public UserDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) UserDto userDto) {
        UserInfo userInfo = userInfoMapper.userDtoToUser(userDto);
        userInfo = userService.save(userInfo);
        return userInfoMapper.userToUserDto(userInfo);
    }

}
