package com.solvd.laba.iis.web.dto.group;

import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Data
public class GroupDto {

    @Null(groups = OnCreateGroup.class, message = "Group's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateLessonGroup.class, OnCreateStudentGroup.class}, message = "Group's id should be filled")
    private Long id;

    @NotNull(message = "Number of group should be filled")
    @Digits(integer = 6, fraction = 0)
    private Integer number;

}
