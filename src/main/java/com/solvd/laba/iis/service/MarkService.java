package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;

import java.util.List;

public interface MarkService {

    List<Mark> retrieveAll();

    Mark retrieveById(Long id);

    List<Mark> retrieveByTeacher(Long subjectId, Long teacherId);

    List<Mark> retrieveByCriteria(Long studentId, MarkSearchCriteria markSearchCriteria);

    Mark create(Mark mark);

    Mark update(Mark mark);

    void delete(Long id);

}
