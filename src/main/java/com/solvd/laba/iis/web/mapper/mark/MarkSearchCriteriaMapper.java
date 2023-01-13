package com.solvd.laba.iis.web.mapper.mark;

import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.web.dto.criteria.MarkSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkSearchCriteriaMapper {

    MarkSearchCriteria dtoToEntity(MarkSearchCriteriaDto markSearchCriteriaDto);

}
