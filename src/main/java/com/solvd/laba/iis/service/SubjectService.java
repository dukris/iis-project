package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> retrieveAll();

    Subject retrieveById(Long id);

    Subject create(Subject subject);

    Subject update(Subject subject);

    void delete(Long id);

}
