package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, GroupMapper.class})
public interface StudentInfoMapper {

    StudentInfoDto studentInfoToStudentInfoDto(StudentInfo studentInfo);

    StudentInfo studentInfoDtoToStudentInfo(StudentInfoDto studentInfoDto);

    List<StudentInfoDto> listToListDto(List<StudentInfo> studentInfos);

    List<StudentInfo> listDtoToList(List<StudentInfoDto> studentInfoDtos);

}
