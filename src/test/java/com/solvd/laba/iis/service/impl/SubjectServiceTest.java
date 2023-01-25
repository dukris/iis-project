package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.SubjectRepository;
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
public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    public void verifyRetrieveAllTest() {
        List<Subject> expectedSubjects = createSubjects();
        when(subjectRepository.findAll()).thenReturn(expectedSubjects);
        List<Subject> subjects = subjectService.retrieveAll();
        assertEquals(expectedSubjects, subjects, "Objects are not equal");
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        Subject expectedSubject = createSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.of(expectedSubject));
        Subject subject = subjectService.retrieveById(expectedSubject.getId());
        assertEquals(expectedSubject, subject, "Objects are not equal");
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        Subject expectedSubject = createSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> subjectService.retrieveById(expectedSubject.getId()));
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
    }

    @Test
    public void verifyRetrieveByTeacherTest() {
        List<Subject> expectedSubjects = createSubjects();
        when(subjectRepository.findByTeacher(1L)).thenReturn(expectedSubjects);
        List<Subject> subjects = subjectService.retrieveByTeacher(1L);
        assertEquals(expectedSubjects, subjects, "Objects are not equal");
        verify(subjectRepository, times(1)).findByTeacher(1L);
    }

    @Test
    public void verifyCreateSuccessTest() {
        Subject expectedSubject = createSubject();
        when(subjectRepository.isExist(expectedSubject.getName())).thenReturn(false);
        Subject subject = subjectService.create(expectedSubject);
        assertThat(subject).isNotNull();
        verify(subjectRepository, times(1)).isExist(expectedSubject.getName());
        verify(subjectRepository, times(1)).create(expectedSubject);
    }

    @Test
    public void verifyCreateThrowsResourceAlreadyExistExceptionTest() {
        Subject expectedSubject = createSubject();
        when(subjectRepository.isExist(expectedSubject.getName())).thenReturn(true);
        assertThrows(ResourceAlreadyExistException.class, () -> subjectService.create(expectedSubject));
        verify(subjectRepository, times(1)).isExist(expectedSubject.getName());
        verify(subjectRepository, times(0)).create(expectedSubject);
    }

    @Test
    public void verifyUpdateSuccessTest() {
        Subject expectedSubject = createSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.of(expectedSubject));
        Subject subject = subjectService.update(expectedSubject);
        assertThat(subject).isNotNull();
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
        verify(subjectRepository, times(1)).update(expectedSubject);
    }

    @Test
    public void verifyUpdateThrowsResourceDoesNotExistExceptionTest() {
        Subject expectedSubject = createSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> subjectService.update(expectedSubject));
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
        verify(subjectRepository, times(0)).update(expectedSubject);
    }

    @Test
    public void verifyDeleteTest() {
        subjectService.delete(1L);
        verify(subjectRepository, times(1)).delete(1L);
    }

    private Subject createSubject() {
        return new Subject(1L, "Maths");
    }

    private List<Subject> createSubjects() {
        return Lists.newArrayList(
                new Subject(1L, "Maths"),
                new Subject(2L, "Programming"),
                new Subject(3L, "Physics"));
    }

}
