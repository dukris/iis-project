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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        when(markRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> markService.retrieveById(1L));
        verify(markRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyRetrieveByStudentCriteriaTest() {
        List<Mark> expectedMarks = createMarks();
        when(markRepository.findByCriteria(1L, new MarkSearchCriteria())).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveByCriteria(1L, new MarkSearchCriteria());
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findByCriteria(1L, new MarkSearchCriteria());
    }

    @Test
    public void verifyRetrieveByStudentAndSubjectCriteriaTest() {
        List<Mark> expectedMarks = createMarks();
        when(markRepository.findByCriteria(1L, new MarkSearchCriteria(1L))).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveByCriteria(1L, new MarkSearchCriteria(1L));
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findByCriteria(1L, new MarkSearchCriteria(1L));
    }

    @Test
    public void verifyRetrieveByTeacherTest() {
        List<Mark> expectedMarks = createMarks();
        when(markRepository.findBySubjectAndTeacher(1L, 1L)).thenReturn(expectedMarks);
        List<Mark> marks = markService.retrieveByTeacher(1L, 1L);
        assertEquals(expectedMarks, marks, "Objects are not equal");
        verify(markRepository, times(1)).findBySubjectAndTeacher(1L, 1L);
    }

    @Test
    public void verifyCreateTest() {
        Mark expectedMark = createMark();
        Mark mark = markService.create(expectedMark);
        assertThat(mark).isNotNull();
        verify(markRepository, times(1)).create(expectedMark);
    }

    @Test
    public void verifyUpdateTest() {
        Mark expectedMark = createMark();
        when(markRepository.findById(expectedMark.getId())).thenReturn(Optional.of(expectedMark));
        Mark mark = markService.update(expectedMark);
        assertThat(mark).isNotNull();
        verify(markRepository, times(1)).findById(expectedMark.getId());
        verify(markRepository, times(1)).update(expectedMark);
    }

    @Test
    public void verifyDeleteTest() {
        markService.delete(1L);
        verify(markRepository, times(1)).delete(1L);
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
