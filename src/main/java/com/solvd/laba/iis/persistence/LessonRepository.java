package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LessonRepository {

    List<Lesson> findAll();

    Optional<Lesson> findById(Long id);

    List<Lesson> findByStudentCriteria(@Param("groupId") Long groupId, @Param("lessonSearchCriteria") LessonSearchCriteria lessonSearchCriteria);

    List<Lesson> findByTeacherCriteria(@Param("teacherId")Long teacherId, @Param("lessonSearchCriteria") LessonSearchCriteria lessonSearchCriteria);

    void create(Lesson lesson);

    void update(Lesson lesson);

    void delete(Long id);

}
