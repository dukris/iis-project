package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.TeacherInfo;

import java.util.List;

public interface TeacherService {

    List<TeacherInfo> retrieveAll();

    TeacherInfo retrieveById(Long id);

    TeacherInfo retrieveByUserId(Long userId);

    List<TeacherInfo> retrieveByGroup(Long groupId);

    List<TeacherInfo> retrieveBySubject(Long subjectId);

    TeacherInfo create(TeacherInfo teacherInfo);

    TeacherInfo update(TeacherInfo teacherInfo);

    void delete(Long id);

    void deleteSubjectForTeacher(Long teacherId, Long subjectId);

    void addSubjectForTeacher(Long teacherId, Long subjectId);

}
