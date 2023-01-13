package com.solvd.laba.iis.domain.student;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.group.Group;
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
    private UserInfo user;
    private Group group;

}
