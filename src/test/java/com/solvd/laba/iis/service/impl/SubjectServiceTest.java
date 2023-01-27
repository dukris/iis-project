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
        Long subjectId = 1L;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> subjectService.retrieveById(subjectId));
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    public void verifyRetrieveByTeacherTest() {
        List<Subject> expectedSubjects = createSubjects();
        Long teacherId = 1L;
        when(subjectRepository.findByTeacher(teacherId)).thenReturn(expectedSubjects);
        List<Subject> subjects = subjectService.retrieveByTeacher(teacherId);
        assertEquals(expectedSubjects, subjects, "Objects are not equal");
        verify(subjectRepository, times(1)).findByTeacher(teacherId);
    }

    @Test
    public void verifyCreateSuccessTest() {
        Long subjectId = 1L;
        Subject subject = createSubject();
        subject.setId(null);
        when(subjectRepository.isExist(anyString())).thenReturn(false);
        doAnswer(invocation -> {
            Subject receivedSubject = invocation.getArgument(0);
            receivedSubject.setId(subjectId);
            return null;
        }).when(subjectRepository).create(subject);
        subject = subjectService.create(subject);
        assertEquals(subjectId, subject.getId(), "Objects are not equal");
        verify(subjectRepository, times(1)).isExist(anyString());
        verify(subjectRepository, times(1)).create(any(Subject.class));
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
    public void verifyUpdateTest() {
        Subject oldSubject = createSubject();
        Subject newSubject = createSubject();
        newSubject.setName("New subject");
        when(subjectRepository.findById(newSubject.getId())).thenReturn(Optional.of(oldSubject));
        Subject subject = subjectService.update(newSubject);
        assertEquals(newSubject, subject, "Objects are not equal");
        verify(subjectRepository, times(1)).findById(newSubject.getId());
        verify(subjectRepository, times(1)).update(newSubject);
    }

    @Test
    public void verifyDeleteTest() {
        Long subjectId = 1L;
        subjectService.delete(subjectId);
        verify(subjectRepository, times(1)).delete(subjectId);
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
