package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.web.dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    @Mapping(target = "password", ignore = true)
    UserInfoDto userToUserDto(UserInfo userInfo);

    UserInfo userDtoToUser(UserInfoDto userInfoDto);

    @Mapping(target = "password", ignore = true)
    List<UserInfoDto> listToListDto(List<UserInfo> userInfos);

    List<UserInfo> listDtoToList(List<UserInfoDto> userInfoDtos);

}
