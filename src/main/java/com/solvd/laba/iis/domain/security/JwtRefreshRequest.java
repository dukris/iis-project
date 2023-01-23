package com.solvd.laba.iis.domain.security;

import lombok.Data;

@Data
public class JwtRefreshRequest {

    public String refreshToken;

}
