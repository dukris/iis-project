package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.lesson.Lesson;
import com.solvd.laba.iis.domain.lesson.LessonSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {

    List<Lesson> findAll();

    Optional<Lesson> findById(Long id);

    List<Lesson> findByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> findByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria);

    void create(Lesson lesson);

    void save(Lesson lesson);

    void delete(Long id);

}
