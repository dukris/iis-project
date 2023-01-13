package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;

import java.util.List;

public interface LessonService {

    List<Lesson> retrieveAll();

    Lesson retrieveById(Long id);

    List<Lesson> retrieveByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> retrieveByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria);

    Lesson create(Lesson lesson);

    Lesson update(Lesson lesson);

    void delete(Long id);

}
