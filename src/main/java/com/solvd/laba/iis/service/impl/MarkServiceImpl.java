package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.jdbc.MarkRepository;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    @Override
    public List<Mark> retrieveAll() {
        return markRepository.findAll();
    }

    @Override
    public Mark retrieveById(Long id) {
        return markRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Mark with id = " + id + " not found"));
    }

    @Override
    public List<Mark> retrieveByCriteria(Long studentId, MarkSearchCriteria markSearchCriteria) {
        return markRepository.findByCriteria(studentId, markSearchCriteria);
    }

    @Override
    public List<Mark> retrieveByTeacher(Long subjectId, Long teacherId) {
        return markRepository.findBySubjectAndTeacher(subjectId, teacherId);
    }

    @Override
    @Transactional
    public Mark create(Mark mark) {
        markRepository.create(mark);
        return mark;
    }

    @Override
    @Transactional
    public Mark update(Mark mark) {
        Mark foundMark = retrieveById(mark.getId());
        foundMark.setDate(mark.getDate());
        foundMark.setValue(mark.getValue());
        foundMark.setStudent(mark.getStudent());
        foundMark.setTeacher(mark.getTeacher());
        foundMark.setSubject(mark.getSubject());
        markRepository.update(foundMark);
        return foundMark;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        markRepository.delete(id);
    }

}
