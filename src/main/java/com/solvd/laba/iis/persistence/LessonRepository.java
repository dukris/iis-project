package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Lesson;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    List<Lesson> findAll();

    Optional<Lesson> findById(long id);

    List<Lesson> findByGroup(long groupId);

    List<Lesson> findByGroupAndDay(long groupId, String weekday);

    List<Lesson> findByTeacher(long teacherId);

    List<Lesson> findByTeacherAndDay(long teacherId, String weekday);

    Lesson create(Lesson lesson);

    Lesson save(Lesson lesson);

    void delete(Lesson lesson);
}
