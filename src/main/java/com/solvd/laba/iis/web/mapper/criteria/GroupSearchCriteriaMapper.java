package com.solvd.laba.iis.web.mapper.criteria;

import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.web.dto.criteria.GroupSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupSearchCriteriaMapper {

    GroupSearchCriteriaDto groupCriteriaToGroupCriteriaDto(GroupSearchCriteria groupSearchCriteria);

    GroupSearchCriteria groupCriteriaDtoToGroupCriteria(GroupSearchCriteriaDto groupSearchCriteriaDto);

}
