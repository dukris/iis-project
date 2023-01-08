package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Mark;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {

    List<Mark> findBySubjectAndTeacher(long subjectId, long teacherId);

    List<Mark> findByStudent(long studentId);

    List<Mark> findByStudentAndSubject(long studentId, long subjectId);

    @Transactional
    Mark create(Mark mark);

    @Transactional
    Mark save(Mark mark);

    @Transactional
    void delete(Mark mark);
}
