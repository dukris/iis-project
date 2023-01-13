package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.group.Group;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.GroupRepository;
import com.solvd.laba.iis.domain.group.GroupSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Group with id = " + id + " not found"));
    }

    @Override
    public List<Group> findByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria) {
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
        findById(group.getId());
        groupRepository.save(group);
        return group;
    }

    @Override
    public void delete(Long id) {
        groupRepository.delete(id);
    }

}
