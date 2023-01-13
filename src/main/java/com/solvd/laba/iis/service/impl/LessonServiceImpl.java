package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.lesson.Lesson;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.LessonRepository;
import com.solvd.laba.iis.domain.lesson.LessonSearchCriteria;
import com.solvd.laba.iis.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Lesson with id = " + id + " not found"));
    }

    @Override
    public List<Lesson> findByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria) {
        return lessonRepository.findByStudentCriteria(groupId, lessonSearchCriteria);
    }

    @Override
    public List<Lesson> findByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria) {
        return lessonRepository.findByTeacherCriteria(teacherId, lessonSearchCriteria);
    }

    @Override
    public Lesson create(Lesson lesson) {
        lessonRepository.create(lesson);
        return lesson;
    }

    @Override
    public Lesson save(Lesson lesson) {
        findById(lesson.getId());
        lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public void delete(Long id) {
        lessonRepository.delete(id);
    }

}
