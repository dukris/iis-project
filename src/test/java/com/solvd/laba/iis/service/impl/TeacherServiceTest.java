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
        TeacherInfo expectedTeacher = createTeacher();
        when(teacherRepository.findById(expectedTeacher.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> teacherService.retrieveById(expectedTeacher.getId()));
        verify(teacherRepository, times(1)).findById(expectedTeacher.getId());
    }

    @Test
    public void verifyRetrieveByUserIdSuccessTest() {
        TeacherInfo expectedTeacher = createTeacher();
        when(teacherRepository.findByUserId(1L)).thenReturn(Optional.of(expectedTeacher));
        TeacherInfo teacher = teacherService.retrieveByUserId(1L);
        assertEquals(expectedTeacher, teacher, "Objects are not equal");
        verify(teacherRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void verifyRetrieveByUserIdThrowsResourceDoesNotExistExceptionTest() {
        when(teacherRepository.findByUserId(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> teacherService.retrieveByUserId(1L));
        verify(teacherRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void verifyRetrieveByMarkIdSuccessTest() {
        TeacherInfo expectedTeacher = createTeacher();
        when(teacherRepository.findByMarkId(1L)).thenReturn(Optional.of(expectedTeacher));
        TeacherInfo teacher = teacherService.retrieveByMarkId(1L);
        assertEquals(expectedTeacher, teacher, "Objects are not equal");
        verify(teacherRepository, times(1)).findByMarkId(1L);
    }

    @Test
    public void verifyRetrieveByMarkIdThrowsResourceDoesNotExistExceptionTest() {
        when(teacherRepository.findByMarkId(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> teacherService.retrieveByMarkId(1L));
        verify(teacherRepository, times(1)).findByMarkId(1L);
    }

    @Test
    public void verifyRetrieveByGroupTest() {
        List<TeacherInfo> expectedTeachers = createTeachers();
        when(teacherRepository.findByGroup(1L)).thenReturn(expectedTeachers);
        List<TeacherInfo> teachers = teacherService.retrieveByGroup(1L);
        assertEquals(expectedTeachers, teachers, "Objects are not equal");
        verify(teacherRepository, times(1)).findByGroup(1L);
    }

    @Test
    public void verifyRetrieveBySubjectTest() {
        List<TeacherInfo> expectedTeachers = createTeachers();
        when(teacherRepository.findBySubject(1L)).thenReturn(expectedTeachers);
        List<TeacherInfo> teachers = teacherService.retrieveBySubject(1L);
        assertEquals(expectedTeachers, teachers, "Objects are not equal");
        verify(teacherRepository, times(1)).findBySubject(1L);
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
        teacherService.delete(1L);
        verify(teacherRepository, times(1)).delete(1L);
    }

    @Test
    public void verifyDeleteSubjectForTeacherTest() {
        TeacherInfo expectedTeacher = createTeacher();
        teacherService.deleteSubjectForTeacher(expectedTeacher.getId(), 1L);
        verify(teacherRepository, times(1)).deleteSubject(expectedTeacher.getId(), 1L);
    }

    @Test
    public void verifyAddSubjectForTeacherTest() {
        TeacherInfo expectedTeacher = createTeacher();
        teacherService.addSubjectForTeacher(expectedTeacher.getId(), 1L);
        verify(teacherRepository, times(1)).addSubject(expectedTeacher.getId(), 1L);
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
