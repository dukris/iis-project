package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceNotFoundException;
import com.solvd.laba.iis.persistence.SubjectRepository;
import com.solvd.laba.iis.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getById(long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with id = " + id + " not found"));
    }

    @Override
    public Subject create(Subject subject) {
        subjectRepository.findByName(subject.getName())
                .ifPresent(el -> {
                    throw new ResourceAlreadyExistsException("Subject with name = " + subject.getName() + " already exists");
                });
        subjectRepository.create(subject);
        return subject;
    }

    @Override
    public Subject save(Subject subject) {
        getById(subject.getId());
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public void delete(Subject subject) {
        subjectRepository.delete(subject);
    }

}
