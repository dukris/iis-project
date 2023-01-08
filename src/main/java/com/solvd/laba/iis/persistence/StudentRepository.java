package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.StudentInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<StudentInfo> findAll();

    Optional<StudentInfo> findById(long id);

    List<StudentInfo> findByGroup(long groupId);

    List<StudentInfo> findBySpeciality(String speciality);

    List<StudentInfo> findByFaculty(String faculty);

    List<StudentInfo> findByAdmissionYear(int year);

    @Transactional
    StudentInfo create(StudentInfo studentInfo);

    @Transactional
    StudentInfo save(StudentInfo studentInfo);

    @Transactional
    void delete(StudentInfo studentInfo);
}
