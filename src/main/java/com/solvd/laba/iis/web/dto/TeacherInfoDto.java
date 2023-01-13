package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateTeacherGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.List;

@Data
public class TeacherInfoDto {

    @Null(groups = OnCreateTeacherGroup.class, message = "Teacher's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateLessonGroup.class, OnCreateMarkGroup.class}, message = "Teacher's id should be filled")
    private Long id;

    @NotNull(message = "Teacher should contain user")
    @Valid
    private UserInfoDto user;

    private List<SubjectDto> subjects;

}