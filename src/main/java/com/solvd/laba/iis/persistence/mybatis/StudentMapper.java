package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface StudentMapper {
    //TODO add mapper.xml

    List<StudentInfo> findAll();

    Optional<StudentInfo> findById(Long id);

    List<StudentInfo> findByCriteria(StudentSearchCriteria studentSearchCriteria);

    List<StudentInfo> findByGroup(Long groupId);

    void create(StudentInfo studentInfo);

    void update(StudentInfo studentInfo);

    void delete(Long id);

}
