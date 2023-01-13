package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, SubjectMapper.class})
public interface TeacherInfoMapper {

    TeacherInfoDto entityToDto(TeacherInfo teacherInfo);

    TeacherInfo dtoToEntity(TeacherInfoDto teacherInfoDto);

    List<TeacherInfoDto> entityToDto(List<TeacherInfo> teacherInfos);

}
