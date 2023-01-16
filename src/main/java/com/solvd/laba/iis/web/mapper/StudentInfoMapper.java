package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, GroupMapper.class})
public interface StudentInfoMapper {

    StudentInfoDto entityToDto(StudentInfo studentInfo);

    StudentInfo dtoToEntity(StudentInfoDto studentInfoDto);

    List<StudentInfoDto> entityToDto(List<StudentInfo> studentInfos);

}
