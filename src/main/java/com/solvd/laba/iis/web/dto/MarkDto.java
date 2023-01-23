package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Schema(description = "Information about mark")
public class MarkDto {

    @Null(groups = OnCreateMarkGroup.class, message = "Mark's id should be empty")
    @NotNull(groups = OnUpdateGroup.class, message = "Mark's id should be filled")
    @Schema(description = "Mark's id")
    private Long id;

    @NotNull(message = "Date of mark should be filled")
    @Schema(description = "Date of mark")
    private LocalDate date;

    @NotNull(message = "Value of mark should be filled")
    @Digits(integer = 2, fraction = 0)
    @Min(value = 1, message = "Min value of mark is {min}")
    @Max(value = 10, message = "Min value of mark is {max}")
    @Schema(description = "Value of mark")
    private Integer value;

    @NotNull(message = "Mark should contain student")
    @Valid
    private StudentInfoDto student;

    @NotNull(message = "Mark should contain teacher")
    @Valid
    private TeacherInfoDto teacher;

    @NotNull(message = "Mark should contain subject")
    @Valid
    private SubjectDto subject;

}

