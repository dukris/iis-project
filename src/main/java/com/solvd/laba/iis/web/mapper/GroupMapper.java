package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.web.dto.GroupDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDto groupToGroupDto(Group group);

    Group groupDtoToGroup(GroupDto groupDto);

    List<GroupDto> listToListDto(List<Group> groups);

    List<Group> listDtoToList(List<GroupDto> groupDtos);

}
