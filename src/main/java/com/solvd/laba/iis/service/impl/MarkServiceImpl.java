package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.exception.ResourceNotFoundException;
import com.solvd.laba.iis.persistence.MarkRepository;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    @Override
    public List<Mark> getAll() {
        return markRepository.findAll();
    }

    @Override
    public Mark getById(long id) {
        return markRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mark with id = " + id + " not found"));
    }

    @Override
    public List<Mark> getByCriteria(long studentId, MarkSearchCriteria markSearchCriteria) {
        return markRepository.findByCriteria(studentId, markSearchCriteria);
    }

    @Override
    public List<Mark> getByTeacher(long subjectId, long teacherId) {
        return markRepository.findBySubjectAndTeacher(subjectId, teacherId);
    }

    @Override
    public Mark create(Mark mark) {
        markRepository.create(mark);
        return mark;
    }

    @Override
    public Mark save(Mark mark) {
        getById(mark.getId());
        markRepository.save(mark);
        return mark;
    }

    @Override
    public void delete(Mark mark) {
        markRepository.delete(mark);
    }

}
