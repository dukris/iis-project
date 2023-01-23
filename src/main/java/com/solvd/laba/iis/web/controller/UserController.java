package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.security.JwtRefreshRequest;
import com.solvd.laba.iis.domain.security.JwtRequest;
import com.solvd.laba.iis.domain.security.JwtResponse;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.dto.UserInfoDto;
import com.solvd.laba.iis.web.dto.security.JwtRefreshRequestDto;
import com.solvd.laba.iis.web.dto.security.JwtRequestDto;
import com.solvd.laba.iis.web.dto.security.JwtResponseDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.UserInfoMapper;
import com.solvd.laba.iis.web.mapper.security.JwtRefreshRequestMapper;
import com.solvd.laba.iis.web.mapper.security.JwtRequestMapper;
import com.solvd.laba.iis.web.mapper.security.JwtResponseMapper;
import com.solvd.laba.iis.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "Methods for working with users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserInfoMapper userInfoMapper;
    private final JwtRequestMapper jwtRequestMapper;
    private final JwtResponseMapper jwtResponseMapper;
    private final JwtRefreshRequestMapper jwtRefreshRequestMapper;

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserInfoDto> getAll() {
        List<UserInfo> users = userService.retrieveAll();
        List<UserInfoDto> userDtos = userInfoMapper.entityToDto(users);
        return userDtos;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public UserInfoDto getById(@PathVariable @Parameter(description = "User's id") Long id) {
        UserInfo user = userService.retrieveById(id);
        UserInfoDto userDto = userInfoMapper.entityToDto(user);
        return userDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new user")
    public UserInfoDto create(@RequestBody @Validated(OnCreateGroup.class) @Parameter(description = "Information about user") UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.dtoToEntity(userInfoDto);
        userInfo = userService.create(userInfo);
        userInfoDto = userInfoMapper.entityToDto(userInfo);
        return userInfoDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping
    @Operation(summary = "Update information about user")
    public UserInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) @Parameter(description = "Information about user") UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoMapper.dtoToEntity(userInfoDto);
        userInfo = userService.update(userInfo);
        userInfoDto = userInfoMapper.entityToDto(userInfo);
        return userInfoDto;
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public JwtResponseDto login(@RequestBody @Parameter(description = "JWT Request") JwtRequestDto jwtRequestDto) {
        JwtRequest jwtRequest = jwtRequestMapper.dtoToEntity(jwtRequestDto);
        JwtResponse jwtResponse = authenticationService.login(jwtRequest);
        JwtResponseDto jwtResponseDto = jwtResponseMapper.entityToDto(jwtResponse);
        return jwtResponseDto;
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh tokens")
    public JwtResponseDto refresh(@RequestBody @Parameter(description = "JWT Refresh request") JwtRefreshRequestDto jwtRefreshRequestDto) {
        JwtRefreshRequest jwtRefreshRequest = jwtRefreshRequestMapper.dtoToEntity(jwtRefreshRequestDto);
        JwtResponse jwtResponse = authenticationService.refresh(jwtRefreshRequest);
        JwtResponseDto jwtResponseDto = jwtResponseMapper.entityToDto(jwtResponse);
        return jwtResponseDto;
    }

}
