package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.group.GroupSearchCriteria;
import com.solvd.laba.iis.domain.lesson.LessonSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.group.GroupDto;
import com.solvd.laba.iis.web.dto.lesson.LessonDto;
import com.solvd.laba.iis.web.dto.mark.MarkDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.group.GroupSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.lesson.LessonSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateTeacherGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.group.GroupMapper;
import com.solvd.laba.iis.web.mapper.lesson.LessonMapper;
import com.solvd.laba.iis.web.mapper.mark.MarkMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import com.solvd.laba.iis.web.mapper.group.GroupSearchCriteriaMapper;
import com.solvd.laba.iis.web.mapper.lesson.LessonSearchCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final GroupService groupService;
    private final LessonService lessonService;
    private final MarkService markService;
    private final TeacherInfoMapper teacherInfoMapper;
    private final GroupMapper groupMapper;
    private final GroupSearchCriteriaMapper groupSearchCriteriaMapper;
    private final LessonMapper lessonMapper;
    private final LessonSearchCriteriaMapper lessonSearchCriteriaMapper;
    private final MarkMapper markMapper;

    @GetMapping
    public List<TeacherInfoDto> getAll() {
        List<TeacherInfoDto> teachers = teacherInfoMapper.entityToDto(teacherService.findAll());
        return teachers;
    }

    @GetMapping("/{id}")
    public TeacherInfoDto getById(@PathVariable long id) {
        TeacherInfoDto teacher = teacherInfoMapper.entityToDto(teacherService.findById(id));
        return teacher;
    }

    @GetMapping("/{id}/groups")
    public List<GroupDto> getGroups(@PathVariable long id,
                                    GroupSearchCriteriaDto groupSearchCriteriaDto) {
        GroupSearchCriteria groupSearchCriteria = groupSearchCriteriaMapper.dtoToEntity(groupSearchCriteriaDto);
        List<GroupDto> groups = groupMapper.entityToDto(groupService.findByCriteria(id, groupSearchCriteria));
        return groups;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.dtoToEntity(lessonSearchCriteriaDto);
        List<LessonDto> lessons = lessonMapper.entityToDto(lessonService.findByTeacherCriteria(id, lessonSearchCriteria));
        return lessons;
    }

    @GetMapping("/{id}/subjects/{subject_id}/marks")
    public List<MarkDto> getMarks(@PathVariable long id,
                                  @PathVariable(name = "subject_id") long subjectId) {
        List<MarkDto> marks = markMapper.entityToDto(markService.findByTeacher(subjectId, id));
        return marks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherInfoDto create(@RequestBody @Validated(OnCreateTeacherGroup.class) TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.dtoToEntity(teacherInfoDto);
        teacherInfo = teacherService.create(teacherInfo);
        teacherInfoDto = teacherInfoMapper.entityToDto(teacherInfo);
        return teacherInfoDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        teacherService.delete(id);
    }

    @PutMapping
    public TeacherInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.dtoToEntity(teacherInfoDto);
        teacherInfo = teacherService.save(teacherInfo);
        teacherInfoDto = teacherInfoMapper.entityToDto(teacherInfo);
        return teacherInfoDto;
    }

    @PostMapping("/{id}/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubject(@PathVariable long id,
                           @RequestParam(name = "subject_id") long subjectId) {
        teacherService.addSubjectForTeacher(id, subjectId);
    }

    @DeleteMapping("/{id}/subjects/{subject_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@PathVariable long id,
                              @PathVariable(name = "subject_id") long subjectId) {
        teacherService.deleteSubjectForTeacher(id, subjectId);
    }

}

