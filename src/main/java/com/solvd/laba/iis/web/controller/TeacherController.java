package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.GroupDto;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.criteria.GroupSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.criteria.LessonSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateTeacherGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import com.solvd.laba.iis.web.mapper.criteria.GroupSearchCriteriaMapper;
import com.solvd.laba.iis.web.mapper.criteria.LessonSearchCriteriaMapper;
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
    private final TeacherInfoMapper teacherInfoMapper;
    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final GroupSearchCriteriaMapper groupSearchCriteriaMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final LessonSearchCriteriaMapper lessonSearchCriteriaMapper;
    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping
    public List<TeacherInfoDto> getAll() {
        List<TeacherInfoDto> teachers = teacherInfoMapper.listToListDto(teacherService.getAll());
        return teachers;
    }

    @GetMapping("/{id}")
    public TeacherInfoDto getById(@PathVariable long id) {
        TeacherInfoDto teacher = teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherService.getById(id));
        return teacher;
    }

    @GetMapping("/{id}/groups")
    public List<GroupDto> getGroups(@PathVariable long id,
                                    GroupSearchCriteriaDto groupSearchCriteriaDto) {
        GroupSearchCriteria groupSearchCriteria = groupSearchCriteriaMapper.groupCriteriaDtoToGroupCriteria(groupSearchCriteriaDto);
        List<GroupDto> groups = groupMapper.listToListDto(groupService.getByCriteria(id, groupSearchCriteria));
        return groups;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.lessonCriteriaDtoToLessonCriteria(lessonSearchCriteriaDto);
        List<LessonDto> lessons = lessonMapper.listToListDto(lessonService.getByTeacherCriteria(id, lessonSearchCriteria));
        return lessons;
    }

    @GetMapping("/{id}/subjects/{subject_id}/marks")
    public List<MarkDto> getMarks(@PathVariable long id,
                                  @PathVariable(name = "subject_id") long subjectId) {
        List<MarkDto> marks = markMapper.listToListDto(markService.getByTeacher(subjectId, id));
        return marks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherInfoDto create(@RequestBody @Validated(OnCreateTeacherGroup.class) TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.create(teacherInfo);
        return teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherService.delete(teacherInfo);
    }

    @PutMapping
    public TeacherInfoDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.save(teacherInfo);
        return teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo);
    }

    @PostMapping("/subject")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubject(@RequestParam long teacherId,
                           @RequestParam long subjectId) {
        teacherService.addSubject(teacherId, subjectId);
    }

    @DeleteMapping("/subject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@RequestParam long teacherId,
                              @RequestParam long subjectId) {
        teacherService.deleteSubject(teacherId, subjectId);
    }

}

