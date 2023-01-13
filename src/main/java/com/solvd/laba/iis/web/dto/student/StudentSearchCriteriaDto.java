package com.solvd.laba.iis.web.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentSearchCriteriaDto {

    private String speciality;
    private String faculty;
    private int year;

}
