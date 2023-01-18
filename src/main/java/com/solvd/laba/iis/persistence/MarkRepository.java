package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MarkRepository {

    List<Mark> findAll();

    Optional<Mark> findById(Long id);

    List<Mark> findBySubjectAndTeacher(@Param("subjectId") Long subjectId, @Param("teacherId") Long teacherId);

    List<Mark> findByCriteria(@Param("studentId") Long studentId, @Param("markSearchCriteria") MarkSearchCriteria markSearchCriteria);

    void create(Mark mark);

    void update(Mark mark);

    void delete(Long id);

}
