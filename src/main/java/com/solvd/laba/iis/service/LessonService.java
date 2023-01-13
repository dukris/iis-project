package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.lesson.Lesson;
import com.solvd.laba.iis.domain.lesson.LessonSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LessonService {

    List<Lesson> findAll();

    Lesson findById(Long id);

    List<Lesson> findByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> findByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria);

    @Transactional
    Lesson create(Lesson lesson);

    @Transactional
    Lesson save(Lesson lesson);

    @Transactional
    void delete(Long id);

}
