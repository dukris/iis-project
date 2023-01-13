package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.GroupRepository;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public List<Group> retrieveAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group retrieveById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Group with id = " + id + " not found"));
    }

    @Override
    public List<Group> retrieveByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria) {
        return groupRepository.findByCriteria(teacherId, groupSearchCriteria);
    }

    @Override
    @Transactional
    public Group create(Group group) {
        if (groupRepository.isExist(group.getNumber())) {
            throw new ResourceAlreadyExistsException("Group with number = " + group.getNumber() + " already exists");
        }
        groupRepository.create(group);
        return group;
    }

    @Override
    @Transactional
    public Group update(Group group) {
        Group foundGroup = retrieveById(group.getId());
        foundGroup.setNumber(group.getNumber());
        groupRepository.update(foundGroup);
        return foundGroup;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        groupRepository.delete(id);
    }

}
