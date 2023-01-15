package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectMapper {

    List<Subject> findAll();

    Optional<Subject> findById(Long id);

    boolean isExist(String name);

    void create(Subject subject);

    void update(Subject subject);

    void delete(Long id);

}
