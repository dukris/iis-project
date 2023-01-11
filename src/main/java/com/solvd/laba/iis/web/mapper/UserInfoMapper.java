package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.web.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserDto userToUserDto(UserInfo userInfo);

    UserInfo userDtoToUser(UserDto userDto);

    List<UserDto> listToListDto(List<UserInfo> userInfos);

    List<UserInfo> listDtoToList(List<UserDto> userDtos);

}
