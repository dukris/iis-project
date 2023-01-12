package com.solvd.laba.iis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TeacherInfo {

    private Long id;
    private UserInfo user;
    private List<Subject> subjects;

}
