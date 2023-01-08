package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SubjectMapper.class})
public interface TeacherInfoMapper {
    TeacherInfoDto teacherInfoToTeacherInfoDto(TeacherInfo teacherInfo);
    TeacherInfo teacherInfoDtoToTeacherInfo(TeacherInfoDto teacherInfoDto);
}
