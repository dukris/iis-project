package com.solvd.laba.iis.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MarkDto {
    private long id;
    private LocalDate date;
    private int value;
    private StudentInfoDto student;
    private TeacherInfoDto teacher;
    private SubjectDto subject;
}

