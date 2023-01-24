package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Information about student")
public class StudentInfoDto {

    @Null(groups = OnCreateStudentGroup.class, message = "Student's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateMarkGroup.class}, message = "Student's id should be filled")
    @Schema(description = "Student's id")
    private Long id;

    @NotNull(message = "Year of admission should be filled")
    @Digits(integer = 5, fraction = 0)
    @Schema(description = "Year of admission")
    private Integer admissionYear;

    @NotBlank(message = "Faculty should be filled")
    @Size(max = 50, message = "Max length of faculty is {max}")
    @Schema(description = "Name of faculty")
    private String faculty;

    @NotBlank(message = "Speciality should be filled")
    @Size(max = 50, message = "Max length of speciality is {max}")
    @Schema(description = "Name of speciality")
    private String speciality;

    @NotNull(message = "Student should contain user")
    @Valid
    private UserInfoDto user;

    @NotNull(message = "Student should contain group")
    @Valid
    private GroupDto group;

}

