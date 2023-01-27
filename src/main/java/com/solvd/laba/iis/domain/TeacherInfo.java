package com.solvd.laba.iis.domain;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfo {

    private Long id;
    private UserInfo user;
    private List<Subject> subjects;

}
