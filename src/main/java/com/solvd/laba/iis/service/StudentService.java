package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.persistence.criteria.StudentSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentService {
    List<StudentInfo> getAll();

    StudentInfo getById(long id);

    List<StudentInfo> getByGroup(long groupId);

    List<StudentInfo> getByCriteria(StudentSearchCriteria studentSearchCriteria);

//    List<StudentInfo> getBySpeciality(String speciality);
//
//    List<StudentInfo> getByFaculty(String faculty);
//
//    List<StudentInfo> getByAdmissionYear(int year);

    @Transactional
    StudentInfo create(StudentInfo studentInfo);

    @Transactional
    StudentInfo save(StudentInfo studentInfo);

    @Transactional
    void delete(StudentInfo studentInfo);
}
