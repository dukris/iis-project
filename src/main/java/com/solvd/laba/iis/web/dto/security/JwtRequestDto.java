package com.solvd.laba.iis.web.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtRequestDto {

    @NotBlank(message = "Email should be filled")
    private String email;

    @NotBlank(message = "Password should be filled")
    private String password;

}