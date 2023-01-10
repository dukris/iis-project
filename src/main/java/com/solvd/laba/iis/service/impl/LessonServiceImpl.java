package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.exception.ResourceNotFoundException;
import com.solvd.laba.iis.persistence.LessonRepository;
import com.solvd.laba.iis.persistence.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getById(long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id = " + id + "not found"));
    }

    @Override
    public List<Lesson> getByCriteria(long groupId, LessonSearchCriteria lessonSearchCriteria) {
        return Objects.nonNull(lessonSearchCriteria.getWeekday()) ?
                lessonRepository.findByGroupAndDay(groupId, lessonSearchCriteria.getWeekday()) :
                lessonRepository.findByGroup(groupId);
    }

//    @Override
//    public List<Lesson> getByGroup(long groupId) {
//        return lessonRepository.findByGroup(groupId);
//    }
//
//    @Override
//    public List<Lesson> getByGroupAndDay(long groupId, String weekday) {
//        return lessonRepository.findByGroupAndDay(groupId, weekday);
//    }

    @Override
    public List<Lesson> getByTeacher(long teacherId) {
        return  lessonRepository.findByTeacher(teacherId);
    }

    @Override
    public List<Lesson> getByTeacherAndDay(long teacherId, String weekday) {
        return lessonRepository.findByTeacherAndDay(teacherId, weekday);
    }

    @Override
    public Lesson create(Lesson lesson) {
        return lessonRepository.create(lesson);
    }

    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }
}
