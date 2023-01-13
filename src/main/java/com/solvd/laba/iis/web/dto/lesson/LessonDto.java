package com.solvd.laba.iis.web.dto.lesson;

import com.solvd.laba.iis.domain.lesson.Lesson;
import com.solvd.laba.iis.web.dto.SubjectDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.group.GroupDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalTime;

@Data
public class LessonDto {

    @Null(groups = OnCreateLessonGroup.class, message = "Lesson's id should be empty")
    @NotNull(groups = OnUpdateGroup.class, message = "Lesson's id should be filled")
    private Long id;

    @NotNull(message = "Room should be filled")
    @Digits(integer = 2, fraction = 0)
    @Min(value = 1, message = "Min number of room is {min}")
    private Integer room;

    @NotNull(message = "Day should be filled")
    private Lesson.Weekday weekday;

    @NotNull(message = "Start time should be filled")
    private LocalTime startTime;

    @NotNull(message = "End time should be filled")
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

