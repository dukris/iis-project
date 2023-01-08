package com.solvd.laba.iis.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentInfoDto {
    private long id;
    private int admissionYear;
    private String faculty;
    private String speciality;
    private UserDto user;
    private GroupDto group;

}

