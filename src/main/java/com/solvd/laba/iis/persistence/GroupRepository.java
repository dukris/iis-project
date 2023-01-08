package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Group;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> findAll();

    Optional<Group> findById(long id);

    List<Group> findByTeacher(long teacherId);

    List<Group> findByTeacherAndSubject(long teacherId, long subjectId);

    @Transactional
    Group create(Group group);

    @Transactional
    Group save(Group group);

    @Transactional
    void delete(Group group);
}
