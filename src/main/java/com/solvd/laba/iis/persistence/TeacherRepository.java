package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {
    List<TeacherInfo> findAll();

    Optional<TeacherInfo> findById(long id);

    List<TeacherInfo> findByGroup(long groupId);

    List<TeacherInfo> findBySubject(long subjectId);

    @Transactional
    TeacherInfo create(TeacherInfo teacherInfo);

    @Transactional
    TeacherInfo save(TeacherInfo teacherInfo);

    @Transactional
    void delete(TeacherInfo teacherInfo);

    @Transactional
    void deleteSubject(long teacherId, long subjectId);

    @Transactional
    void addSubject(long teacherId, long subjectId);
}
