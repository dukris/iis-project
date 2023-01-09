package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.Mark;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {
    List<Mark> findAll();

    Optional<Mark> findById(long id);

    List<Mark> findBySubjectAndTeacher(long subjectId, long teacherId);

    List<Mark> findByStudent(long studentId);

    List<Mark> findByStudentAndSubject(long studentId, long subjectId);

    Mark create(Mark mark);

    Mark save(Mark mark);

    void delete(Mark mark);
}
