package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.group.Group;
import com.solvd.laba.iis.domain.group.GroupSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupService {

    List<Group> findAll();

    Group findById(Long id);

    List<Group> findByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria);

    @Transactional
    Group create(Group group);

    @Transactional
    Group save(Group group);

    @Transactional
    void delete(Long id);

}
