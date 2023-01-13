package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserInfoDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
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
        List<UserInfoDto> users = userInfoMapper.entityToDto(userService.findAll());
        return users;
    }

    @GetMapping("/{id}")
    public UserInfoDto getById(@PathVariable long id) {
        UserInfoDto user = userInfoMapper.entityToDto(userService.findById(id));
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDto create(@RequestBody @Validated(OnCreateGroup.class) UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.dtoToEntity(userInfoDto);
        userInfo = userService.create(userInfo);
        userInfoDto = userInfoMapper.entityToDto(userInfo);
        return userInfoDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

    @PutMapping
    public UserInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.dtoToEntity(userInfoDto);
        userInfo = userService.save(userInfo);
        userInfoDto = userInfoMapper.entityToDto(userInfo);
        return userInfoDto;
    }

}
