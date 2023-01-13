package com.solvd.laba.iis.web.mapper.student;

import com.solvd.laba.iis.domain.student.StudentSearchCriteria;
import com.solvd.laba.iis.web.dto.student.StudentSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentSearchCriteriaMapper {

    StudentSearchCriteria dtoToEntity(StudentSearchCriteriaDto studentSearchCriteriaDto);

}
