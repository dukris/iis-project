package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TeacherRepository {

    List<TeacherInfo> findAll();

    Optional<TeacherInfo> findById(Long id);

    Optional<TeacherInfo> findByUserId(Long userId);

    List<TeacherInfo> findByGroup(Long groupId);

    List<TeacherInfo> findBySubject(Long subjectId);

    void create(TeacherInfo teacherInfo);

    void update(TeacherInfo teacherInfo);

    void delete(Long id);

    void deleteSubject(@Param("teacherId") Long teacherId, @Param("subjectId") Long subjectId);

    void addSubject(@Param("teacherId") Long teacherId, @Param("subjectId") Long subjectId);

}
