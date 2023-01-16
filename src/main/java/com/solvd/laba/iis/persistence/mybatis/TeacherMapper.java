package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.TeacherInfo;

import java.util.List;
import java.util.Optional;

public interface TeacherMapper {
    //TODO add mapper.xml

    List<TeacherInfo> findAll();

    Optional<TeacherInfo> findById(Long id);

    List<TeacherInfo> findByGroup(Long groupId);

    List<TeacherInfo> findBySubject(Long subjectId);

    void create(TeacherInfo teacherInfo);

    void update(TeacherInfo teacherInfo);

    void delete(Long id);

    void deleteSubject(Long teacherId, Long subjectId);

    void addSubject(Long teacherId, Long subjectId);

}
