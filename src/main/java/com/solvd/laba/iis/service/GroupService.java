package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;

import java.util.List;

public interface GroupService {

    List<Group> retrieveAll();

    Group retrieveById(Long id);

    List<Group> retrieveByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria);

    Group create(Group group);

    Group update(Group group);

    void delete(Long id);

}
