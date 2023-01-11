package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, SubjectMapper.class})
public interface TeacherInfoMapper {

    TeacherInfoDto teacherInfoToTeacherInfoDto(TeacherInfo teacherInfo);

    TeacherInfo teacherInfoDtoToTeacherInfo(TeacherInfoDto teacherInfoDto);

    List<TeacherInfoDto> listToListDto(List<TeacherInfo> teacherInfos);

    List<TeacherInfo> listDtoToList(List<TeacherInfoDto> teacherInfoDtos);

}
