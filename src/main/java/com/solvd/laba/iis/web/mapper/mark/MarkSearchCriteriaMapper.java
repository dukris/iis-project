package com.solvd.laba.iis.web.mapper.mark;

import com.solvd.laba.iis.domain.mark.MarkSearchCriteria;
import com.solvd.laba.iis.web.dto.mark.MarkSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkSearchCriteriaMapper {

    MarkSearchCriteria dtoToEntity(MarkSearchCriteriaDto markSearchCriteriaDto);

}
