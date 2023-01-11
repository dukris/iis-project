package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupService {

    List<Group> getAll();

    Group getById(long id);

    List<Group> getByCriteria(long teacherId, GroupSearchCriteria groupSearchCriteria);

    @Transactional
    Group create(Group group);

    @Transactional
    Group save(Group group);

    @Transactional
    void delete(Group group);

}
