package com.solvd.laba.iis.domain.lesson;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.group.Group;
import lombok.*;

import java.time.LocalTime;

@Data
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
