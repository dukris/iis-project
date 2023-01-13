package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class SubjectDto {

    @Null(groups = OnCreateGroup.class, message = "Subject's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateLessonGroup.class, OnCreateMarkGroup.class}, message = "Subject's id should be filled")
    private Long id;

    @NotBlank(message = "Name of subject should be filled")
    @Size(max = 50, message = "Max length of name of subject is {max}")
    private String name;

}
