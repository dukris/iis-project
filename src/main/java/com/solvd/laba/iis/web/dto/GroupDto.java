package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Data
@Schema(description = "Information about group")
public class GroupDto {

    @Null(groups = OnCreateGroup.class, message = "Group's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateLessonGroup.class, OnCreateStudentGroup.class}, message = "Group's id should be filled")
    @Schema(description = "Group's id")
    private Long id;

    @NotNull(message = "Number of group should be filled")
    @Digits(integer = 6, fraction = 0)
    @Schema(description = "Number of group")
    private Integer number;

}
