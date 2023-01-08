package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.exception.ServiceException;
import com.solvd.laba.iis.persistence.GroupRepository;
import com.solvd.laba.iis.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group getById(long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Group with id = " + id + "not found"));
    }

    @Override
    public List<Group> getByTeacher(long teacherId) {
        return groupRepository.findByTeacher(teacherId);
    }

    @Override
    public List<Group> getByTeacherAndSubject(long teacherId, long subjectId) {
        return groupRepository.findByTeacherAndSubject(teacherId, subjectId);
    }

    @Override
    public Group create(Group group) {
        return groupRepository.create(group);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }
}
