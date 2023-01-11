package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.exception.ResourceNotFoundException;
import com.solvd.laba.iis.persistence.LessonRepository;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new ResourceNotFoundException("Lesson with id = " + id + " not found"));
    }

    @Override
    public List<Lesson> getByStudentCriteria(long groupId, LessonSearchCriteria lessonSearchCriteria) {
        return lessonRepository.findByStudentCriteria(groupId, lessonSearchCriteria);
    }

    @Override
    public List<Lesson> getByTeacherCriteria(long teacherId, LessonSearchCriteria lessonSearchCriteria) {
        return lessonRepository.findByTeacherCriteria(teacherId, lessonSearchCriteria);
    }

    @Override
    public Lesson create(Lesson lesson) {
        lessonRepository.create(lesson);
        return lesson;
    }

    @Override
    public Lesson save(Lesson lesson) {
        getById(lesson.getId());
        lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

}
