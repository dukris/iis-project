package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAll();

    Group getById(long id);

    List<Group> getByTeacher(long teacherId);

    List<Group> getByTeacherAndSubject(long teacherId, long subjectId);

    Group create(Group group);

    Group save(Group group);

    void delete(Group group);
}
