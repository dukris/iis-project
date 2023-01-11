package com.solvd.laba.iis.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solvd.laba.iis.web.dto.validation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherInfoDto {

    @Null(groups = OnCreateTeacherGroup.class, message = "Teacher's id should be empty")
    @NotNull(groups = {OnUpdateAndDeleteGroup.class, OnCreateLessonGroup.class, OnCreateMarkGroup.class}, message = "Teacher's id should be filled")
    private Long id;

    @NotNull(message = "Teacher should contain user")
    @Valid
    private UserDto user;
    private List<SubjectDto> subjects;

}