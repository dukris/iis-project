package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalTime;

@Data
@Schema(description = "Information about lesson")
public class LessonDto {

    @Null(groups = OnCreateLessonGroup.class, message = "Lesson's id should be empty")
    @NotNull(groups = OnUpdateGroup.class, message = "Lesson's id should be filled")
    @Schema(description = "Lesson's id")
    private Long id;

    @NotNull(message = "Room should be filled")
    @Digits(integer = 2, fraction = 0)
    @Min(value = 1, message = "Min number of room is {min}")
    @Schema(description = "Number of room for lesson")
    private Integer room;

    @NotNull(message = "Day should be filled")
    @Schema(description = "Weekday")
    private Lesson.Weekday weekday;

    @NotNull(message = "Start time should be filled")
    @Schema(description = "Start time of lesson")
    private LocalTime startTime;

    @NotNull(message = "End time should be filled")
    @Schema(description = "End time of lesson")
    private LocalTime endTime;

    @NotNull(message = "Lesson should contain subject")
    @Valid
    private SubjectDto subject;

    @NotNull(message = "Lesson should contain group")
    @Valid
    private GroupDto group;

    @NotNull(message = "Lesson should contain teacher")
    @Valid
    private TeacherInfoDto teacher;

}

