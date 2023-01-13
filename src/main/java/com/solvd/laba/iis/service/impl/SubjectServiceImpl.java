package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
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
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Subject with id = " + id + " not found"));
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
        findById(subject.getId());
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public void delete(Long id) {
        subjectRepository.delete(id);
    }

}
