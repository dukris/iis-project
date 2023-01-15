package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.jdbc.SubjectRepository;
import com.solvd.laba.iis.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> retrieveAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject retrieveById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Subject with id = " + id + " not found"));
    }

    @Override
    @Transactional
    public Subject create(Subject subject) {
        if (subjectRepository.isExist(subject.getName())) {
            throw new ResourceAlreadyExistsException("Subject with name = " + subject.getName() + " already exists");
        }
        subjectRepository.create(subject);
        return subject;
    }

    @Override
    @Transactional
    public Subject update(Subject subject) {
        Subject foundSubject = retrieveById(subject.getId());
        foundSubject.setName(subject.getName());
        subjectRepository.update(subject);
        return subject;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        subjectRepository.delete(id);
    }

}
