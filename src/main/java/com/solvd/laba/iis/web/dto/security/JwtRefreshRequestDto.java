package com.solvd.laba.iis.web.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "JWT Refresh response")
public class JwtRefreshRequestDto {

    @NotBlank(message = "Refresh token should be filled")
    @Schema(description = "Refresh token")
    public String refreshToken;

}
