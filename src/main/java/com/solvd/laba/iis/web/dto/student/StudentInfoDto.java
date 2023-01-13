package com.solvd.laba.iis.web.dto.student;

import com.solvd.laba.iis.web.dto.UserInfoDto;
import com.solvd.laba.iis.web.dto.group.GroupDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentInfoDto {

    @Null(groups = OnCreateStudentGroup.class, message = "Student's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateMarkGroup.class}, message = "Student's id should be filled")
    private Long id;

    @NotNull(message = "Year of admission should be filled")
    @Digits(integer = 5, fraction = 0)
    private Integer admissionYear;

    @NotBlank(message = "Faculty should be filled")
    @Size(max = 50, message = "Max length of faculty is {max}")
    private String faculty;

    @NotBlank(message = "Speciality should be filled")
    @Size(max = 50, message = "Max length of speciality is {max}")
    private String speciality;

    @NotNull(message = "Student should contain user")
    @Valid
    private UserInfoDto user;

    @NotNull(message = "Student should contain group")
    @Valid
    private GroupDto group;

}

