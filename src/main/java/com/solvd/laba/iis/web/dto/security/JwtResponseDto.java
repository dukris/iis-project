package com.solvd.laba.iis.web.dto.security;

import lombok.Data;

@Data
public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;

}
