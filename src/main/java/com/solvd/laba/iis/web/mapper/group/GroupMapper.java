package com.solvd.laba.iis.web.mapper.group;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.web.dto.GroupDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDto entityToDto(Group group);

    Group dtoToEntity(GroupDto groupDto);

    List<GroupDto> entityToDto(List<Group> groups);

}
