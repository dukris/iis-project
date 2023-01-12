package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateTeacherGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
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
public class TeacherInfoDto {

    @Null(groups = OnCreateTeacherGroup.class, message = "Teacher's id should be empty")
    @NotNull(groups = {OnUpdateAndDeleteGroup.class, OnCreateLessonGroup.class, OnCreateMarkGroup.class}, message = "Teacher's id should be filled")
    private Long id;

    @NotNull(message = "Teacher should contain user")
    @Valid
    private UserDto user;
    private List<SubjectDto> subjects;

}