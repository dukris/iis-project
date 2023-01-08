package com.solvd.laba.iis.web.mapper;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.web.dto.LessonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, SubjectMapper.class,  TeacherInfoMapper.class})
public interface LessonMapper {
    LessonDto lessonToLessonDto(Lesson lesson);
    Lesson lessonDtoToLesson(LessonDto lessonDto);
}
