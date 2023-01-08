package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GroupMapper.class})
public interface StudentInfoMapper {
    StudentInfoDto studentInfoToStudentInfoDto(StudentInfo studentInfo);
    StudentInfo studentInfoDtoToStudentInfo(StudentInfoDto studentInfoDto);
}
