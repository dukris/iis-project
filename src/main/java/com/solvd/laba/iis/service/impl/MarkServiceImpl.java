package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.persistence.MarkRepository;
import com.solvd.laba.iis.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {
    private final MarkRepository markRepository;

    @Override
    public List<Mark> getBySubjectAndTeacher(long subjectId, long teacherId) {
        return markRepository.findBySubjectAndTeacher(subjectId, teacherId);
    }

    @Override
    public List<Mark> getByStudent(long studentId) {
        return markRepository.findByStudent(studentId);
    }

    @Override
    public List<Mark> getByStudentAndSubject(long studentId, long subjectId) {
        return markRepository.findByStudentAndSubject(studentId, subjectId);
    }

    @Override
    public Mark create(Mark mark) {
        return markRepository.create(mark);
    }

    @Override
    public Mark save(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public void delete(Mark mark) {
        markRepository.delete(mark);
    }
}
