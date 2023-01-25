package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.TeacherRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    public void verifyRetrieveAllTest() {
        List<TeacherInfo> expectedTeachers = createTeachers();
        when(teacherRepository.findAll()).thenReturn(expectedTeachers);
        List<TeacherInfo> teachers = teacherService.retrieveAll();
        assertEquals(expectedTeachers, teachers, "Objects are not equal");
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        TeacherInfo expectedTeacher = createTeacher();
        when(teacherRepository.findById(expectedTeacher.getId())).thenReturn(Optional.of(expectedTeacher));
        TeacherInfo teacher = teacherService.retrieveById(expectedTeacher.getId());
        assertEquals(expectedTeacher, teacher, "Objects are not equal");
        verify(teacherRepository, times(1)).findById(expectedTeacher.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        Long teacherId = 1L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> teacherService.retrieveById(teacherId));
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    public void verifyRetrieveByUserIdSuccessTest() {
        TeacherInfo expectedTeacher = createTeacher();
        Long userId = 1L;
        when(teacherRepository.findByUserId(userId)).thenReturn(Optional.of(expectedTeacher));
        TeacherInfo teacher = teacherService.retrieveByUserId(userId);
        assertEquals(expectedTeacher, teacher, "Objects are not equal");
        verify(teacherRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void verifyRetrieveByUserIdThrowsResourceDoesNotExistExceptionTest() {
        Long userId = 1L;
        when(teacherRepository.findByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> teacherService.retrieveByUserId(userId));
        verify(teacherRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void verifyRetrieveByMarkIdSuccessTest() {
        TeacherInfo expectedTeacher = createTeacher();
        Long markId = 1L;
        when(teacherRepository.findByMarkId(markId)).thenReturn(Optional.of(expectedTeacher));
        TeacherInfo teacher = teacherService.retrieveByMarkId(markId);
        assertEquals(expectedTeacher, teacher, "Objects are not equal");
        verify(teacherRepository, times(1)).findByMarkId(markId);
    }

    @Test
    public void verifyRetrieveByMarkIdThrowsResourceDoesNotExistExceptionTest() {
        Long markId = 1L;
        when(teacherRepository.findByMarkId(markId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> teacherService.retrieveByMarkId(markId));
        verify(teacherRepository, times(1)).findByMarkId(markId);
    }

    @Test
    public void verifyRetrieveByGroupTest() {
        List<TeacherInfo> expectedTeachers = createTeachers();
        Long groupId = 1L;
        when(teacherRepository.findByGroup(groupId)).thenReturn(expectedTeachers);
        List<TeacherInfo> teachers = teacherService.retrieveByGroup(groupId);
        assertEquals(expectedTeachers, teachers, "Objects are not equal");
        verify(teacherRepository, times(1)).findByGroup(groupId);
    }

    @Test
    public void verifyRetrieveBySubjectTest() {
        List<TeacherInfo> expectedTeachers = createTeachers();
        Long subjectId = 1L;
        when(teacherRepository.findBySubject(subjectId)).thenReturn(expectedTeachers);
        List<TeacherInfo> teachers = teacherService.retrieveBySubject(subjectId);
        assertEquals(expectedTeachers, teachers, "Objects are not equal");
        verify(teacherRepository, times(1)).findBySubject(subjectId);
    }

    @Test
    public void verifyCreateTest() {
        TeacherInfo expectedTeacher = createTeacher();
        TeacherInfo teacher = teacherService.create(expectedTeacher);
        assertThat(teacher).isNotNull();
        verify(teacherRepository, times(1)).create(expectedTeacher);
    }

    @Test
    public void verifyUpdateTest() {
        TeacherInfo expectedTeacher = createTeacher();
        when(teacherRepository.findById(expectedTeacher.getId())).thenReturn(Optional.of(expectedTeacher));
        TeacherInfo teacher = teacherService.update(expectedTeacher);
        assertThat(teacher).isNotNull();
        verify(teacherRepository, times(1)).findById(expectedTeacher.getId());
        verify(teacherRepository, times(1)).update(expectedTeacher);
    }

    @Test
    public void verifyDeleteTest() {
        Long teacherId = 1L;
        teacherService.delete(teacherId);
        verify(teacherRepository, times(1)).delete(teacherId);
    }

    @Test
    public void verifyDeleteSubjectForTeacherTest() {
        Long subjectId = 1L, teacherId = 1L;
        teacherService.deleteSubjectForTeacher(teacherId, subjectId);
        verify(teacherRepository, times(1)).deleteSubject(teacherId, subjectId);
    }

    @Test
    public void verifyAddSubjectForTeacherTest() {
        Long subjectId = 1L, teacherId = 1L;
        teacherService.addSubjectForTeacher(teacherId, subjectId);
        verify(teacherRepository, times(1)).addSubject(teacherId, subjectId);
    }

    private TeacherInfo createTeacher() {
        return new TeacherInfo(1L, new UserInfo(), new ArrayList<>());
    }

    private List<TeacherInfo> createTeachers() {
        return Lists.newArrayList(
                new TeacherInfo(1L, new UserInfo(), new ArrayList<>()),
                new TeacherInfo(2L, new UserInfo(), new ArrayList<>()),
                new TeacherInfo(3L, new UserInfo(), new ArrayList<>()));
    }

}
