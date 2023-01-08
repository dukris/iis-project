package com.solvd.laba.iis.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TeacherInfoDto {
    private long id;
    private UserDto user;
    private List<SubjectDto> subjects;

}
