package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.StudentInfo;

import java.util.List;

public interface StudentService {
    List<StudentInfo> getAll();

    StudentInfo getById(long id);

    List<StudentInfo> getByGroup(long groupId);

    List<StudentInfo> getBySpeciality(String speciality);

    List<StudentInfo> getByFaculty(String faculty);

    List<StudentInfo> getByAdmissionYear(int year);

    StudentInfo create(StudentInfo studentInfo);

    StudentInfo save(StudentInfo studentInfo);

    void delete(StudentInfo studentInfo);
}
