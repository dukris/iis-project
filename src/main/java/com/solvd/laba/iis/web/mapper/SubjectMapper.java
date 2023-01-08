package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.web.dto.SubjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectDto subjectToSubjectDto(Subject subject);
    Subject subjectDtoToSubject(SubjectDto subjectDto);
}

