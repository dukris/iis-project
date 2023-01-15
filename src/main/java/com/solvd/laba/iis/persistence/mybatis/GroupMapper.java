package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GroupMapper {

    List<Group> findAll();

    Optional<Group> findById(Long id);

//    boolean isExist(Integer number);

    List<Group> findByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria);

    void create(Group group);

    void update(Group group);

    void delete(Long id);

}
