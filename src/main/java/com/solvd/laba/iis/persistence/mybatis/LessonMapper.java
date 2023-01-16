package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface LessonMapper {
    //TODO add mapper.xml

    List<Lesson> findAll();

    Optional<Lesson> findById(Long id);

    List<Lesson> findByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> findByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria);

    void create(Lesson lesson);

    void update(Lesson lesson);

    void delete(Long id);

}
