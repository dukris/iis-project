package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {

    List<Lesson> findAll();

    Optional<Lesson> findById(long id);

    List<Lesson> findByStudentCriteria(long groupId, LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> findByTeacherCriteria(long teacherId, LessonSearchCriteria lessonSearchCriteria);

    void create(Lesson lesson);

    void save(Lesson lesson);

    void delete(long id);

}
