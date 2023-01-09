package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Lesson;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LessonService {
    List<Lesson> getAll();

    Lesson getById(long id);

    List<Lesson> getByGroup(long groupId);

    List<Lesson> getByGroupAndDay(long groupId, String weekday);

    List<Lesson> getByTeacher(long teacherId);

    List<Lesson> getByTeacherAndDay(long teacherId, String weekday);

    @Transactional
    Lesson create(Lesson lesson);

    @Transactional
    Lesson save(Lesson lesson);

    @Transactional
    void delete(Lesson lesson);
}
