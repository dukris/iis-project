package com.solvd.laba.iis.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solvd.laba.iis.domain.*;
import com.solvd.laba.iis.web.dto.validation.UpdateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LessonDto {
    @NotNull(groups = UpdateGroup.class, message = "Lesson's id should be filled")
    private long id;
    @NotNull(message = "Room should be filled")
    private int room;
    @NotNull(message = "Day should be filled")
    private Weekday weekday;
    @NotNull(message = "Start time should be filled")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;
    @NotNull(message = "End time should be filled")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
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

