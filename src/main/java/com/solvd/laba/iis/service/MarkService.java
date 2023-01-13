package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MarkService {

    List<Mark> getAll();

    Mark getById(long id);

    List<Mark> getByTeacher(long subjectId, long teacherId);

    List<Mark> getByCriteria(long studentId, MarkSearchCriteria markSearchCriteria);

    @Transactional
    Mark create(Mark mark);

    @Transactional
    Mark save(Mark mark);

    @Transactional
    void delete(long id);

}
