package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.web.dto.MarkDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentInfoMapper.class, SubjectMapper.class, TeacherInfoMapper.class})
public interface MarkMapper {

    MarkDto entityToDto(Mark mark);

    Mark dtoToEntity(MarkDto markDto);

    List<MarkDto> entityToDto(List<Mark> marks);

}
