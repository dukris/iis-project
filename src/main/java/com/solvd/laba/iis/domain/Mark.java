package com.solvd.laba.iis.domain;

import lombok.*;

import java.time.LocalDate;

@Data
public class Mark {

    private Long id;
    private LocalDate date;
    private Integer value;
    private StudentInfo student;
    private TeacherInfo teacher;
    private Subject subject;

}
