package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.TeacherInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeacherService {

    List<TeacherInfo> findAll();

    TeacherInfo findById(Long id);

    List<TeacherInfo> findByGroup(Long groupId);

    List<TeacherInfo> findBySubject(Long subjectId);

    @Transactional
    TeacherInfo create(TeacherInfo teacherInfo);

    @Transactional
    TeacherInfo save(TeacherInfo teacherInfo);

    @Transactional
    void delete(Long id);

    @Transactional
    void deleteSubjectForTeacher(Long teacherId, Long subjectId);

    @Transactional
    void addSubjectForTeacher(Long teacherId, Long subjectId);

}
