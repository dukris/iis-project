package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MarkDto {

    @Null(groups = OnCreateMarkGroup.class, message = "Mark's id should be empty")
    @NotNull(groups = OnUpdateAndDeleteGroup.class, message = "Mark's id should be filled")
    private Long id;

    @NotNull(message = "Date of mark should be filled")
    private LocalDate date;

    @NotNull(message = "Value of mark should be filled")
    @Digits(integer = 3, fraction = 0)
    @Min(value = 1, message = "Min value of mark is {min}")
    @Max(value = 10, message = "Min value of mark is {max}")
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

