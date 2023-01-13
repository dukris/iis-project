package com.solvd.laba.iis.web.mapper.lesson;

import com.solvd.laba.iis.domain.lesson.LessonSearchCriteria;
import com.solvd.laba.iis.web.dto.lesson.LessonSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonSearchCriteriaMapper {

    LessonSearchCriteria dtoToEntity(LessonSearchCriteriaDto lessonSearchCriteriaDto);

}
