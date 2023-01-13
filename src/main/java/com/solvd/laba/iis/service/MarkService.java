package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.mark.Mark;
import com.solvd.laba.iis.domain.mark.MarkSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MarkService {

    List<Mark> findAll();

    Mark findById(Long id);

    List<Mark> findByTeacher(Long subjectId, Long teacherId);

    List<Mark> findByCriteria(Long studentId, MarkSearchCriteria markSearchCriteria);

    @Transactional
    Mark create(Mark mark);

    @Transactional
    Mark save(Mark mark);

    @Transactional
    void delete(Long id);

}
