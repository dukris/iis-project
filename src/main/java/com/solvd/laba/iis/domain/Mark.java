package com.solvd.laba.iis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Mark {

    private Long id;
    private LocalDate date;
    private Integer value;
    private StudentInfo student;
    private TeacherInfo teacher;
    private Subject subject;

}
