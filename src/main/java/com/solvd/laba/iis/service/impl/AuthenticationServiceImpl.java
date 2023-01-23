package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.AuthenticationException;
import com.solvd.laba.iis.domain.security.JwtRefreshRequest;
import com.solvd.laba.iis.domain.security.JwtRequest;
import com.solvd.laba.iis.domain.security.JwtResponse;
import com.solvd.laba.iis.service.AuthenticationService;
import com.solvd.laba.iis.service.JwtService;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.security.JwtUser;
import com.solvd.laba.iis.web.security.JwtUserFactory;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        UserInfo user = userService.retrieveByEmail(jwtRequest.getEmail());
        if (BCrypt.checkpw(jwtRequest.getPassword(), user.getPassword())) {
            JwtResponse response = prepareResponse(user);
            return response;
        } else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    @Override
    public JwtResponse refresh(JwtRefreshRequest jwtRefreshRequest) {
        String refreshToken = jwtRefreshRequest.getRefreshToken();
        Claims claims = jwtService.parseToken(refreshToken);
        JwtUser jwtUser = JwtUserFactory.create(claims);
        UserInfo user = userService.retrieveByEmail(jwtUser.getEmail());
        JwtResponse response = prepareResponse(user);
        return response;
    }

    private JwtResponse prepareResponse(UserInfo user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Long expiredMinutes = jwtService.getExpirationTime();
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiredMinutes(expiredMinutes);
        return response;
    }

}
