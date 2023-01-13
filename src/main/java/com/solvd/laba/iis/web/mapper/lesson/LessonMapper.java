package com.solvd.laba.iis.web.mapper.lesson;

import com.solvd.laba.iis.domain.lesson.Lesson;
import com.solvd.laba.iis.web.dto.lesson.LessonDto;
import com.solvd.laba.iis.web.mapper.SubjectMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import com.solvd.laba.iis.web.mapper.group.GroupMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, SubjectMapper.class, TeacherInfoMapper.class})
public interface LessonMapper {

    LessonDto entityToDto(Lesson lesson);

    Lesson dtoToEntity(LessonDto lessonDto);

    List<LessonDto> entityToDto(List<Lesson> lessons);

}
