package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.TeacherInfo;

import java.util.List;

public interface TeacherService {
    List<TeacherInfo> getAll();

    TeacherInfo getById(long id);

    List<TeacherInfo> getByGroup(long groupId);

    List<TeacherInfo> getBySubject(long subjectId);

    TeacherInfo create(TeacherInfo teacherInfo);

    TeacherInfo save(TeacherInfo teacherInfo);

    void delete(TeacherInfo teacherInfo);

    void deleteSubject(long teacherId, long subjectId);

    void addSubject(long teacherId, long subjectId);
}
