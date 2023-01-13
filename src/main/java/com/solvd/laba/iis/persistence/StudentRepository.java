package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.student.StudentInfo;
import com.solvd.laba.iis.domain.student.StudentSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    List<StudentInfo> findAll();

    Optional<StudentInfo> findById(Long id);

    List<StudentInfo> findByCriteria(StudentSearchCriteria studentSearchCriteria);

    List<StudentInfo> findByGroup(Long groupId);

    void create(StudentInfo studentInfo);

    void save(StudentInfo studentInfo);

    void delete(Long id);

}
