package com.solvd.laba.iis.domain.mark;

import com.solvd.laba.iis.domain.student.StudentInfo;
import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Mark {

    private Long id;
    private LocalDate date;
    private Integer value;
    private StudentInfo student;
    private TeacherInfo teacher;
    private Subject subject;

}
