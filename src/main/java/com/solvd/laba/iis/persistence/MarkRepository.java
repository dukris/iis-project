package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {

    List<Mark> findAll();

    Optional<Mark> findById(long id);

    List<Mark> findBySubjectAndTeacher(long subjectId, long teacherId);

    List<Mark> findByCriteria(long studentId, MarkSearchCriteria markSearchCriteria);

    void create(Mark mark);

    void save(Mark mark);

    void delete(long id);

}
