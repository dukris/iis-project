package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentService {

    List<StudentInfo> getAll();

    StudentInfo getById(long id);

    List<StudentInfo> getByGroup(long groupId);

    List<StudentInfo> getByCriteria(StudentSearchCriteria studentSearchCriteria);

    @Transactional
    StudentInfo create(StudentInfo studentInfo);

    @Transactional
    StudentInfo save(StudentInfo studentInfo);

    @Transactional
    void delete(StudentInfo studentInfo);

}
