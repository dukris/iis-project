package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.GroupRepository;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
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
                .orElseThrow(() -> new ResourceDoesNotExistException("Group with id = " + id + " not found"));
    }

    @Override
    public List<Group> getByCriteria(long teacherId, GroupSearchCriteria groupSearchCriteria) {
        return groupRepository.findByCriteria(teacherId, groupSearchCriteria);
    }

    @Override
    public Group create(Group group) {
        groupRepository.findByNumber(group.getNumber())
                .ifPresent(el -> {
                    throw new ResourceAlreadyExistsException("Group with number = " + group.getNumber() + " already exists");
                });
        groupRepository.create(group);
        return group;
    }

    @Override
    public Group save(Group group) {
        getById(group.getId());
        groupRepository.save(group);
        return group;
    }

    @Override
    public void delete(long id) {
        groupRepository.delete(id);
    }

}
