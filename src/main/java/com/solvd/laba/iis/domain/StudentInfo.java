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

    private Long id;
    private Integer admissionYear;
    private String faculty;
    private String speciality;
    private UserInfo userInfo;
    private Group group;

}
