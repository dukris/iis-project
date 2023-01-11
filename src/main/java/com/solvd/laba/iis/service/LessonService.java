package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LessonService {

    List<Lesson> getAll();

    Lesson getById(long id);

    List<Lesson> getByStudentCriteria(long groupId, LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> getByTeacherCriteria(long teacherId, LessonSearchCriteria lessonSearchCriteria);

    @Transactional
    Lesson create(Lesson lesson);

    @Transactional
    Lesson save(Lesson lesson);

    @Transactional
    void delete(Lesson lesson);

}
