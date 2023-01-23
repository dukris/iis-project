package com.solvd.laba.iis.web.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtRefreshRequestDto {

    @NotBlank(message = "Refresh token should be filled")
    public String refreshToken;

}
