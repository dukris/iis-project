package com.solvd.laba.iis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentInfo {
    private long id;
    private int admissionYear;
    private String faculty;
    private String speciality;
    private User user;
    private Group group;
}
