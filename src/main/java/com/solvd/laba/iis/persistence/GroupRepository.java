package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    List<Group> findAll();

    Optional<Group> findById(long id);

    Optional<Group> findByNumber(int number);

    List<Group> findByCriteria(long teacherId, GroupSearchCriteria groupSearchCriteria);

    void create(Group group);

    void save(Group group);

    void delete(long id);

}
