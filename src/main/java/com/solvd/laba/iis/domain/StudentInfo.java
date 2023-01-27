package com.solvd.laba.iis.domain;

import lombok.*;

@Data
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
