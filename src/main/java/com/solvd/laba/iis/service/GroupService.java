package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Group;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupService {
    List<Group> getAll();

    Group getById(long id);

    List<Group> getByTeacher(long teacherId);

    List<Group> getByTeacherAndSubject(long teacherId, long subjectId);

    @Transactional
    Group create(Group group);

    @Transactional
    Group save(Group group);

    @Transactional
    void delete(Group group);
}
