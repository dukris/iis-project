package com.solvd.laba.iis.domain.security;

import lombok.Data;

@Data
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private Long expiredMinutes;

}
