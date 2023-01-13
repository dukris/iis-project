package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Subject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectService {

    List<Subject> findAll();

    Subject findById(Long id);

    @Transactional
    Subject create(Subject subject);

    @Transactional
    Subject save(Subject subject);

    @Transactional
    void delete(Long id);

}
