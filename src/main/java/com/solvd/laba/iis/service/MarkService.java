package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Mark;

import java.util.List;

public interface MarkService {
    List<Mark> getBySubjectAndTeacher(long subjectId, long teacherId);

    List<Mark> getByStudent(long studentId);

    List<Mark> getByStudentAndSubject(long studentId, long subjectId);

    Mark create(Mark mark);

    Mark save(Mark mark);

    void delete(Mark mark);
}
