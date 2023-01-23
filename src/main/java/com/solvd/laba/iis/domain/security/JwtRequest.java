package com.solvd.laba.iis.domain.security;

import lombok.Data;

@Data
public class JwtRequest {

    private String email;
    private String password;

}
