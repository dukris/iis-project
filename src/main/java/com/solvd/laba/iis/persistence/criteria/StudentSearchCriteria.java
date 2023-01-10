package com.solvd.laba.iis.persistence.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentSearchCriteria {
    private String speciality;
    private String faculty;
    private int year;
}
