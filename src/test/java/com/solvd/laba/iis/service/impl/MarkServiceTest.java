package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.MarkRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarkServiceTest {

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private MarkServiceImpl markService;

    @Test
    public void verifyRetrieveAllTest() {
        List<Mark> expectedMarks = createMarks();
        when(markRepository.findAll()).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveAll();
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        Mark expectedMark = createMark();
        when(markRepository.findById(expectedMark.getId())).thenReturn(Optional.of(expectedMark));
        Mark mark = markService.retrieveById(expectedMark.getId());
        assertEquals(expectedMark, mark, "Objects are not equal");
        verify(markRepository, times(1)).findById(expectedMark.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        Long markId = 1L;
        when(markRepository.findById(markId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> markService.retrieveById(markId));
        verify(markRepository, times(1)).findById(markId);
    }

    @Test
    public void verifyRetrieveByStudentCriteriaTest() {
        List<Mark> expectedMarks = createMarks();
        Long studentId = 1L;
        when(markRepository.findByCriteria(studentId, new MarkSearchCriteria())).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveByCriteria(studentId, new MarkSearchCriteria());
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findByCriteria(studentId, new MarkSearchCriteria());
    }

    @Test
    public void verifyRetrieveByStudentAndSubjectCriteriaTest() {
        List<Mark> expectedMarks = createMarks();
        Long studentId = 1L, subjectId = 1L;
        when(markRepository.findByCriteria(studentId, new MarkSearchCriteria(subjectId))).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveByCriteria(studentId, new MarkSearchCriteria(subjectId));
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findByCriteria(studentId, new MarkSearchCriteria(subjectId));
    }

    @Test
    public void verifyRetrieveByTeacherTest() {
        List<Mark> expectedMarks = createMarks();
        Long teacherId = 1L, subjectId = 1L;
        when(markRepository.findBySubjectAndTeacher(subjectId, teacherId)).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveByTeacher(subjectId, teacherId);
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findBySubjectAndTeacher(subjectId, teacherId);
    }

    @Test
    public void verifyCreateTest() {
        Long markId = 1L;
        Mark mark = createMark();
        mark.setId(null);
        doAnswer(invocation -> {
            Mark receivedMark = invocation.getArgument(0);
            receivedMark.setId(markId);
            return null;
        }).when(markRepository).create(mark);
        mark = markService.create(mark);
        assertEquals(markId, mark.getId(), "Objects are not equal");
        verify(markRepository, times(1)).create(any(Mark.class));
    }

    @Test
    public void verifyUpdateTest() {
        Mark oldMark = createMark();
        Mark newMark = createMark();
        newMark.setValue(2);
        when(markRepository.findById(newMark.getId())).thenReturn(Optional.of(oldMark));
        Mark mark = markService.update(newMark);
        assertEquals(newMark, mark, "Objects are not equal");
        verify(markRepository, times(1)).findById(newMark.getId());
        verify(markRepository, times(1)).update(newMark);
    }

    @Test
    public void verifyDeleteTest() {
        Long markId = 1L;
        markService.delete(markId);
        verify(markRepository, times(1)).delete(markId);
    }

    private Mark createMark() {
        return new Mark(1L, LocalDate.of(2022, 1, 13), 8,
                new StudentInfo(), new TeacherInfo(), new Subject());
    }

    private List<Mark> createMarks() {
        return Lists.list(
                new Mark(1L, LocalDate.of(2022, 12, 13), 8, new StudentInfo(), new TeacherInfo(), new Subject()),
                new Mark(2L, LocalDate.of(2023, 1, 2), 8, new StudentInfo(), new TeacherInfo(), new Subject()),
                new Mark(3L, LocalDate.of(2022, 12, 27), 8, new StudentInfo(), new TeacherInfo(), new Subject()));
    }

}
