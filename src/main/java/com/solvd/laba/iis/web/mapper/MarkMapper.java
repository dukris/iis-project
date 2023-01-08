package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.web.dto.MarkDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StudentInfoMapper.class, SubjectMapper.class,  TeacherInfoMapper.class})
public interface MarkMapper {
    MarkDto markToMarkDto(Mark mark);
    Mark markDtoToMark(MarkDto markDto);
}
