package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.StudentRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void verifyRetrieveAllTest() {
        List<StudentInfo> expectedStudents = createStudents();
        when(studentRepository.findAll()).thenReturn(expectedStudents);
        List<StudentInfo> students = studentService.retrieveAll();
        assertEquals(expectedStudents, students, "Objects are not equal");
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        StudentInfo expectedStudent = createStudent();
        when(studentRepository.findById(expectedStudent.getId())).thenReturn(Optional.of(expectedStudent));
        StudentInfo student = studentService.retrieveById(expectedStudent.getId());
        assertEquals(expectedStudent, student, "Objects are not equal");
        verify(studentRepository, times(1)).findById(expectedStudent.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> studentService.retrieveById(1L));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyRetrieveByUserIdSuccessTest() {
        StudentInfo expectedStudent = createStudent();
        when(studentRepository.findByUserId(1L)).thenReturn(Optional.of(expectedStudent));
        StudentInfo student = studentService.retrieveByUserId(1L);
        assertEquals(expectedStudent, student, "Objects are not equal");
        verify(studentRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void verifyRetrieveByUserIdThrowsResourceDoesNotExistExceptionTest() {
        when(studentRepository.findByUserId(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> studentService.retrieveByUserId(1L));
        verify(studentRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void verifyRetrieveByGroupTest() {
        List<StudentInfo> expectedStudents = createStudents();
        when(studentRepository.findByGroup(1L)).thenReturn(expectedStudents);
        List<StudentInfo> students = studentService.retrieveByGroup(1L);
        assertEquals(expectedStudents, students, "Objects are not equal");
        verify(studentRepository, times(1)).findByGroup(1L);
    }

    @Test
    public void verifyRetrieveByEmptyCriteriaTest() {
        List<StudentInfo> expectedStudents = createStudents();
        when(studentRepository.findByCriteria(new StudentSearchCriteria())).thenReturn(expectedStudents);
        List<StudentInfo> students = studentService.retrieveByCriteria(new StudentSearchCriteria());
        assertEquals(expectedStudents, students, "Objects are not equal");
        verify(studentRepository, times(1)).findByCriteria(new StudentSearchCriteria());
    }

    @Test
    public void verifyRetrieveByFacultyCriteriaTest() {
        List<StudentInfo> expectedStudents = createStudents();
        when(studentRepository.findByCriteria(new StudentSearchCriteria(null, "faculty", null))).thenReturn(expectedStudents);
        List<StudentInfo> students = studentService.retrieveByCriteria(new StudentSearchCriteria(null, "faculty", null));
        assertEquals(expectedStudents, students, "Objects are not equal");
        verify(studentRepository, times(1)).findByCriteria(new StudentSearchCriteria(null, "faculty", null));
    }

    @Test
    public void verifyRetrieveBySpecialityCriteriaTest() {
        List<StudentInfo> expectedStudents = createStudents();
        when(studentRepository.findByCriteria(new StudentSearchCriteria("speciality", null, null))).thenReturn(expectedStudents);
        List<StudentInfo> students = studentService.retrieveByCriteria(new StudentSearchCriteria("speciality", null, null));
        assertEquals(expectedStudents, students, "Objects are not equal");
        verify(studentRepository, times(1)).findByCriteria(new StudentSearchCriteria("speciality", null, null));
    }

    @Test
    public void verifyRetrieveByYearCriteriaTest() {
        List<StudentInfo> expectedStudents = createStudents();
        when(studentRepository.findByCriteria(new StudentSearchCriteria(null, null, 2019))).thenReturn(expectedStudents);
        List<StudentInfo> students = studentService.retrieveByCriteria(new StudentSearchCriteria(null, null, 2019));
        assertEquals(expectedStudents, students, "Objects are not equal");
        verify(studentRepository, times(1)).findByCriteria(new StudentSearchCriteria(null, null, 2019));
    }

    @Test
    public void verifyCreateTest() {
        StudentInfo expectedStudent = createStudent();
        StudentInfo student = studentService.create(expectedStudent);
        assertThat(student).isNotNull();
        verify(studentRepository, times(1)).create(expectedStudent);
    }

    @Test
    public void verifyUpdateTest() {
        StudentInfo expectedStudent = createStudent();
        when(studentRepository.findById(expectedStudent.getId())).thenReturn(Optional.of(expectedStudent));
        StudentInfo student = studentService.update(expectedStudent);
        assertThat(student).isNotNull();
        verify(studentRepository, times(1)).findById(expectedStudent.getId());
        verify(studentRepository, times(1)).update(expectedStudent);
    }

    @Test
    public void verifyDeleteTest() {
        studentService.delete(1L);
        verify(studentRepository, times(1)).delete(1L);
    }

    private StudentInfo createStudent() {
        return new StudentInfo(1L, 2019, "faculty", "speciality", new UserInfo(), new Group());
    }

    private List<StudentInfo> createStudents() {
        return Lists.newArrayList(
                new StudentInfo(1L, 2019, "faculty", "speciality", new UserInfo(), new Group()),
                new StudentInfo(2L, 2020, "faculty", "speciality", new UserInfo(), new Group()),
                new StudentInfo(3L, 2021, "faculty", "speciality", new UserInfo(), new Group()));
    }

}
