package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.student.StudentInfo;
import com.solvd.laba.iis.domain.student.StudentSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentService {

    List<StudentInfo> findAll();

    StudentInfo findById(Long id);

    List<StudentInfo> findByGroup(Long groupId);

    List<StudentInfo> findByCriteria(StudentSearchCriteria studentSearchCriteria);

    @Transactional
    StudentInfo create(StudentInfo studentInfo);

    @Transactional
    StudentInfo save(StudentInfo studentInfo);

    @Transactional
    void delete(Long id);

}
