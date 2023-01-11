package com.solvd.laba.iis.web.mapper.criteria;

import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.web.dto.criteria.StudentSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentSearchCriteriaMapper {

    StudentSearchCriteriaDto studentCriteriaToStudentCriteriaDto(StudentSearchCriteria studentSearchCriteria);

    StudentSearchCriteria studentCriteriaDtoToStudentCriteria(StudentSearchCriteriaDto studentSearchCriteriaDto);

}
