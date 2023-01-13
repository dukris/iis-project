package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Subject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectService {

    List<Subject> getAll();

    Subject getById(long id);

    @Transactional
    Subject create(Subject subject);

    @Transactional
    Subject save(Subject subject);

    @Transactional
    void delete(long id);

}
