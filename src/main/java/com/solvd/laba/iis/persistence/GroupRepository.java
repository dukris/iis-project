package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GroupRepository {

    List<Group> findAll();

    Optional<Group> findById(Long id);

    List<Group> findByTeacherAndSubject(@Param("teacherId")Long teacherId, @Param("subjectId") Long subjectId);

    boolean isExist(Integer number);

    List<Group> findByCriteria(@Param("teacherId") Long teacherId, @Param("groupSearchCriteria") GroupSearchCriteria groupSearchCriteria);

    void create(Group group);

    void update(Group group);

    void delete(Long id);

}
