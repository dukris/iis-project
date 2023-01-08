package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.domain.*;
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
    private long id;
    private int room;
    private Weekday weekday;
    private LocalTime startTime;
    private LocalTime endTime;
    private SubjectDto subject;
    private GroupDto group;
    private TeacherInfoDto teacher;
}

