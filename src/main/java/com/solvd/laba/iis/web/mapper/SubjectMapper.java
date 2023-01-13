package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.web.dto.SubjectDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectDto entityToDto(Subject subject);

    Subject dtoToEntity(SubjectDto subjectDto);

    List<SubjectDto> entityToDto(List<Subject> subjects);

    List<Subject> dtoToEntity(List<SubjectDto> subjectDtos);

}

