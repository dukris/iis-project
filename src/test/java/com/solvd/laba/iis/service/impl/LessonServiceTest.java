package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.LessonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    public void verifyRetrieveAllTest() {
        List<Lesson> expectedLessons = createLessons();
        when(lessonRepository.findAll()).thenReturn(expectedLessons);
        List<Lesson> lessons = lessonService.retrieveAll();
        assertEquals(expectedLessons, lessons, "Objects are not equal");
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        Lesson expectedLesson = createLesson();
        when(lessonRepository.findById(expectedLesson.getId())).thenReturn(Optional.of(expectedLesson));
        Lesson lesson = lessonService.retrieveById(expectedLesson.getId());
        assertEquals(expectedLesson, lesson, "Objects are not equal");
        verify(lessonRepository, times(1)).findById(expectedLesson.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> lessonService.retrieveById(1L));
        verify(lessonRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyRetrieveByStudentCriteriaTest() {
        List<Lesson> expectedLessons = createLessons();
        when(lessonRepository.findByStudentCriteria(1L, new LessonSearchCriteria())).thenReturn(expectedLessons);
        List<Lesson> lessons = lessonService.retrieveByStudentCriteria(1L, new LessonSearchCriteria());
        assertEquals(expectedLessons, lessons, "Objects are not equal");
        verify(lessonRepository, times(1)).findByStudentCriteria(1L, new LessonSearchCriteria());
    }

    @Test
    public void verifyRetrieveByStudentAndDayCriteriaTest() {
        List<Lesson> expectedLessons = createLessons();
        when(lessonRepository.findByStudentCriteria(1L, new LessonSearchCriteria(Lesson.Weekday.SATURDAY.name()))).thenReturn(expectedLessons);
        List<Lesson> lessons = lessonService.retrieveByStudentCriteria(1L, new LessonSearchCriteria(Lesson.Weekday.SATURDAY.name()));
        assertEquals(expectedLessons, lessons, "Objects are not equal");
        verify(lessonRepository, times(1)).findByStudentCriteria(1L, new LessonSearchCriteria(Lesson.Weekday.SATURDAY.name()));
    }

    @Test
    public void verifyRetrieveByTeacherCriteriaTest() {
        List<Lesson> expectedLessons = createLessons();
        when(lessonRepository.findByTeacherCriteria(1L, new LessonSearchCriteria())).thenReturn(expectedLessons);
        List<Lesson> lessons = lessonService.retrieveByTeacherCriteria(1L, new LessonSearchCriteria());
        assertEquals(expectedLessons, lessons, "Objects are not equal");
        verify(lessonRepository, times(1)).findByTeacherCriteria(1L, new LessonSearchCriteria());
    }

    @Test
    public void verifyRetrieveByTeacherAndDayCriteriaTest() {
        List<Lesson> expectedLessons = createLessons();
        when(lessonRepository.findByTeacherCriteria(1L, new LessonSearchCriteria(Lesson.Weekday.SATURDAY.name()))).thenReturn(expectedLessons);
        List<Lesson> lessons = lessonService.retrieveByTeacherCriteria(1L, new LessonSearchCriteria(Lesson.Weekday.SATURDAY.name()));
        assertEquals(expectedLessons, lessons, "Objects are not equal");
        verify(lessonRepository, times(1)).findByTeacherCriteria(1L, new LessonSearchCriteria(Lesson.Weekday.SATURDAY.name()));
    }

    @Test
    public void verifyCreateTest() {
        Lesson expectedLesson = createLesson();
        Lesson lesson = lessonService.create(expectedLesson);
        assertThat(lesson).isNotNull();
        verify(lessonRepository, times(1)).create(expectedLesson);
    }

    @Test
    public void verifyUpdateTest() {
        Lesson expectedLesson = createLesson();
        when(lessonRepository.findById(expectedLesson.getId())).thenReturn(Optional.of(expectedLesson));
        Lesson lesson = lessonService.update(expectedLesson);
        assertThat(lesson).isNotNull();
        verify(lessonRepository, times(1)).findById(expectedLesson.getId());
        verify(lessonRepository, times(1)).update(expectedLesson);
    }

    @Test
    public void verifyDeleteTest() {
        lessonService.delete(1L);
        verify(lessonRepository, times(1)).delete(1L);
    }

    private Lesson createLesson() {
        return new Lesson(1L, 505, Lesson.Weekday.MONDAY, LocalTime.of(17, 10), LocalTime.of(18, 30),
                new Subject(), new Group(), new TeacherInfo());
    }

    private List<Lesson> createLessons() {
        return Lists.newArrayList(
                new Lesson(1L, 505, Lesson.Weekday.MONDAY, LocalTime.of(17, 10), LocalTime.of(18, 30),
                        new Subject(), new Group(), new TeacherInfo()),
                new Lesson(2L, 501, Lesson.Weekday.FRIDAY, LocalTime.of(12, 30), LocalTime.of(13, 30),
                        new Subject(), new Group(), new TeacherInfo()),
                new Lesson(3L, 400, Lesson.Weekday.WEDNESDAY, LocalTime.of(14, 50), LocalTime.of(16, 10),
                        new Subject(), new Group(), new TeacherInfo()));
    }

}
