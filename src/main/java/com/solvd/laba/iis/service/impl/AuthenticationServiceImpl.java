package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.AuthenticationException;
import com.solvd.laba.iis.domain.security.JwtRefreshRequest;
import com.solvd.laba.iis.domain.security.JwtRequest;
import com.solvd.laba.iis.domain.security.JwtResponse;
import com.solvd.laba.iis.service.AuthenticationService;
import com.solvd.laba.iis.service.UserService;
import com.solvd.laba.iis.web.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse login(JwtRequest authRequest) {
        UserInfo user = userService.retrieveByEmail(authRequest.getEmail());
        if (BCrypt.checkpw(authRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            JwtResponse response = new JwtResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            return response;
        } else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    @Override
    public JwtResponse refresh(JwtRefreshRequest jwtRefreshRequest) {
        String refreshToken = jwtRefreshRequest.getRefreshToken();
        if (jwtProvider.validateToken(refreshToken)) {
            String email = jwtProvider.getEmail(refreshToken);
            UserInfo user = userService.retrieveByEmail(email);
            String accessToken = jwtProvider.generateAccessToken(user);
            String newRefreshToken = jwtProvider.generateRefreshToken(user);
            JwtResponse response = new JwtResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(newRefreshToken);
            return response;
        }
        throw new AuthenticationException("JWT token is invalid");
    }

}
