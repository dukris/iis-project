package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.mark.Mark;
import com.solvd.laba.iis.domain.mark.MarkSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {

    List<Mark> findAll();

    Optional<Mark> findById(Long id);

    List<Mark> findBySubjectAndTeacher(Long subjectId, Long teacherId);

    List<Mark> findByCriteria(Long studentId, MarkSearchCriteria markSearchCriteria);

    void create(Mark mark);

    void save(Mark mark);

    void delete(Long id);

}
