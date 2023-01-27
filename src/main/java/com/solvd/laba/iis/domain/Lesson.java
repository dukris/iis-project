package com.solvd.laba.iis.domain;

import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    private Long id;
    private Integer room;
    private Weekday weekday;
    private LocalTime startTime;
    private LocalTime endTime;
    private Subject subject;
    private Group group;
    private TeacherInfo teacher;

    public enum Weekday {

        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY

    }

}
