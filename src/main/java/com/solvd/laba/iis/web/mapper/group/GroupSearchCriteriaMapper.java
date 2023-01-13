package com.solvd.laba.iis.web.mapper.group;

import com.solvd.laba.iis.domain.group.GroupSearchCriteria;
import com.solvd.laba.iis.web.dto.group.GroupSearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupSearchCriteriaMapper {

    GroupSearchCriteria dtoToEntity(GroupSearchCriteriaDto groupSearchCriteriaDto);

}
