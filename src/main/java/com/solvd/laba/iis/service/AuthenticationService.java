package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.security.JwtRefreshRequest;
import com.solvd.laba.iis.domain.security.JwtRequest;
import com.solvd.laba.iis.domain.security.JwtResponse;

public interface AuthenticationService {

    JwtResponse login(JwtRequest authRequest);

    JwtResponse refresh(JwtRefreshRequest refreshToken);

}
