package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {

    List<Subject> findAll();

    Optional<Subject> findById(Long id);

    boolean isExist(String name);

    void create(Subject subject);

    void update(Subject subject);

    void delete(Long id);

}
