package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.TeacherInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeacherService {

    List<TeacherInfo> getAll();

    TeacherInfo getById(long id);

    List<TeacherInfo> getByGroup(long groupId);

    List<TeacherInfo> getBySubject(long subjectId);

    @Transactional
    TeacherInfo create(TeacherInfo teacherInfo);

    @Transactional
    TeacherInfo save(TeacherInfo teacherInfo);

    @Transactional
    void delete(long id);

    @Transactional
    void deleteSubject(long teacherId, long subjectId);

    @Transactional
    void addSubject(long teacherId, long subjectId);

}
