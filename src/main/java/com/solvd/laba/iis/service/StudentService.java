package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;

import java.util.List;

public interface StudentService {

    List<StudentInfo> retrieveAll();

    StudentInfo retrieveById(Long id);

    StudentInfo retrieveByUserId(Long userId);

    List<StudentInfo> retrieveByGroup(Long groupId);

    List<StudentInfo> retrieveByCriteria(StudentSearchCriteria studentSearchCriteria);

    StudentInfo create(StudentInfo studentInfo);

    StudentInfo update(StudentInfo studentInfo);

    void delete(Long id);

}
