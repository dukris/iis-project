package com.solvd.laba.iis.web.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "JWT Request")
public class JwtRequestDto {

    @NotBlank(message = "Email should be filled")
    @Schema(description = "Email")
    private String email;

    @NotBlank(message = "Password should be filled")
    @Schema(description = "Password")
    private String password;

}