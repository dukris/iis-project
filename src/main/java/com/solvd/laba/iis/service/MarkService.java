package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.persistence.criteria.MarkSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MarkService {
    List<Mark> getAll();

    Mark getById(long id);

    List<Mark> getByTeacher(long subjectId, long teacherId);
    List<Mark> getByCriteria(long studentId, MarkSearchCriteria markSearchCriteria);

//    List<Mark> getByStudent(long studentId);
//
//    List<Mark> getByStudentAndSubject(long studentId, long subjectId);

    @Transactional
    Mark create(Mark mark);

    @Transactional
    Mark save(Mark mark);

    @Transactional
    void delete(Mark mark);
}
