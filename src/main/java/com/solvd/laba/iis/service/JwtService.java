package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.web.security.JwtUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateAccessToken(UserInfo user);

    String generateRefreshToken(UserInfo user);

    Claims parseToken(String token);

    Long getExpirationTime();

    Authentication getAuthenticationForUser(JwtUser jwtUser);

}
