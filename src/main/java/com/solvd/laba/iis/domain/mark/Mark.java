package com.solvd.laba.iis.domain.mark;

import com.solvd.laba.iis.domain.student.StudentInfo;
import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import lombok.*;

import java.time.LocalDate;

@Data
public class Mark {

    private Long id;
    private LocalDate date;
    private Integer value;
    private StudentInfo student;
    private TeacherInfo teacher;
    private Subject subject;

}
