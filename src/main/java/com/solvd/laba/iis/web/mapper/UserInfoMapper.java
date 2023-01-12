package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.web.dto.UserInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfoDto userToUserDto(UserInfo userInfo);

    UserInfo userDtoToUser(UserInfoDto userInfoDto);

    List<UserInfoDto> listToListDto(List<UserInfo> userInfos);

    List<UserInfo> listDtoToList(List<UserInfoDto> userInfoDtos);

}
