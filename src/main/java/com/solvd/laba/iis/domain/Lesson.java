package com.solvd.laba.iis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Lesson {
    private long id;
    private int room;
    private Weekday weekday;
    private LocalTime startTime;
    private LocalTime endTime;
    private Subject subject;
    private Group group;
    private TeacherInfo teacher;
}
