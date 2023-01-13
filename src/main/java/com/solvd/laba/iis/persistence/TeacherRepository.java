package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.TeacherInfo;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {

    List<TeacherInfo> findAll();

    Optional<TeacherInfo> findById(long id);

    List<TeacherInfo> findByGroup(long groupId);

    List<TeacherInfo> findBySubject(long subjectId);

    void create(TeacherInfo teacherInfo);

    void save(TeacherInfo teacherInfo);

    void delete(long id);

    void deleteSubject(long teacherId, long subjectId);

    void addSubject(long teacherId, long subjectId);

}
