package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.group.Group;
import com.solvd.laba.iis.domain.group.GroupSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    List<Group> findAll();

    Optional<Group> findById(Long id);

    Optional<Group> findByNumber(Integer number);

    List<Group> findByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria);

    void create(Group group);

    void save(Group group);

    void delete(Long id);

}
