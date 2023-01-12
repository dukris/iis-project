package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserInfoDto;
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
    public List<UserInfoDto> getAll() {
        List<UserInfoDto> users = userInfoMapper.listToListDto(userService.getAll());
        return users;
    }

    @GetMapping("/{id}")
    public UserInfoDto getById(@PathVariable long id) {
        UserInfoDto user = userInfoMapper.userToUserDto(userService.getById(id));
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDto create(@RequestBody @Validated(OnCreateGroup.class) UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.userDtoToUser(userInfoDto);
        userInfo = userService.create(userInfo);
        return userInfoMapper.userToUserDto(userInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.userDtoToUser(userInfoDto);
        userService.delete(userInfo);
    }

    @PutMapping
    public UserInfoDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.userDtoToUser(userInfoDto);
        userInfo = userService.save(userInfo);
        return userInfoMapper.userToUserDto(userInfo);
    }

}
