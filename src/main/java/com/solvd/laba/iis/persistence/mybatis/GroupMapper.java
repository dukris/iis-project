package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface GroupMapper{

    List<Group> findAll();

    Optional<Group> findById(Long id);

//    Integer isExist(Integer number);

    List<Group> findByCriteria(@Param("teacherId") Long teacherId, @Param("groupSearchCriteria") GroupSearchCriteria groupSearchCriteria);

    void create(Group group);

    void update(Group group);

    void delete(Long id);

    //TODO add isExist

}
