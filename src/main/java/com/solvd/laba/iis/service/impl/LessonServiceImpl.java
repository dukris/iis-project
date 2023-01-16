package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.LessonRepository;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> retrieveAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson retrieveById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Lesson with id = " + id + " not found"));
    }

    @Override
    public List<Lesson> retrieveByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria) {
        return lessonRepository.findByStudentCriteria(groupId, lessonSearchCriteria);
    }

    @Override
    public List<Lesson> retrieveByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria) {
        return lessonRepository.findByTeacherCriteria(teacherId, lessonSearchCriteria);
    }

    @Override
    @Transactional
    public Lesson create(Lesson lesson) {
        lessonRepository.create(lesson);
        return lesson;
    }

    @Override
    @Transactional
    public Lesson update(Lesson lesson) {
        Lesson foundLesson = retrieveById(lesson.getId());
        foundLesson.setRoom(lesson.getRoom());
        foundLesson.setWeekday(lesson.getWeekday());
        foundLesson.setStartTime(lesson.getStartTime());
        foundLesson.setEndTime(lesson.getEndTime());
        foundLesson.setSubject(lesson.getSubject());
        foundLesson.setGroup(lesson.getGroup());
        foundLesson.setTeacher(lesson.getTeacher());
        lessonRepository.update(foundLesson);
        return foundLesson;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        lessonRepository.delete(id);
    }

}
