package com.solvd.laba.iis.web.mapper.lesson;

import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.web.dto.criteria.LessonSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonSearchCriteriaMapper {

    LessonSearchCriteria dtoToEntity(LessonSearchCriteriaDto lessonSearchCriteriaDto);

}
