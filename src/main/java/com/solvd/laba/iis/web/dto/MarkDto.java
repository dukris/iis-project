package com.solvd.laba.iis.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Value of mark should be filled")
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

