package com.solvd.laba.iis.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Information about error")
public class ErrorDto {

    @Schema(description = "Wrong field")
    private String field;

    @Schema(description = "Error message")
    private String message;

}
