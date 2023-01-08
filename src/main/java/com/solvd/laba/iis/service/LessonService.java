package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getAll();

    Lesson getById(long id);

    List<Lesson> getByGroup(long groupId);

    List<Lesson> getByGroupAndDay(long groupId, String weekday);

    List<Lesson> getByTeacher(long teacherId);

    List<Lesson> getByTeacherAndDay(long teacherId, String weekday);

    Lesson create(Lesson lesson);

    Lesson save(Lesson lesson);

    void delete(Lesson lesson);
}
