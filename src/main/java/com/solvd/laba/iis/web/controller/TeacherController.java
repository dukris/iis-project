package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.Mark;
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
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import com.solvd.laba.iis.web.mapper.criteria.GroupSearchCriteriaMapper;
import com.solvd.laba.iis.web.mapper.criteria.LessonSearchCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
        List<TeacherInfo> teachers = teacherService.retrieveAll();
        return teacherInfoMapper.entityToDto(teachers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAccessForTeacher(#id)")
    public TeacherInfoDto getById(@PathVariable Long id) {
        TeacherInfo teacher = teacherService.retrieveById(id);
        return teacherInfoMapper.entityToDto(teacher);
    }

    @GetMapping("/{id}/groups")
    @PreAuthorize("hasAccessForTeacher(#id)")
    public List<GroupDto> getGroups(@PathVariable Long id,
                                    GroupSearchCriteriaDto groupSearchCriteriaDto) {
        GroupSearchCriteria groupSearchCriteria = groupSearchCriteriaMapper.dtoToEntity(groupSearchCriteriaDto);
        List<Group> groups = groupService.retrieveByCriteria(id, groupSearchCriteria);
        return groupMapper.entityToDto(groups);
    }

    @GetMapping("/{id}/lessons")
    @PreAuthorize("hasAccessForTeacher(#id)")
    public List<LessonDto> getLessons(@PathVariable Long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.dtoToEntity(lessonSearchCriteriaDto);
        List<Lesson> lessons = lessonService.retrieveByTeacherCriteria(id, lessonSearchCriteria);
        return lessonMapper.entityToDto(lessons);
    }

    @GetMapping("/{id}/subjects/{subjectId}/marks")
    @PreAuthorize("hasAccessToSubject(#id, #subjectId)")
    public List<MarkDto> getMarks(@PathVariable Long id,
                                  @PathVariable Long subjectId) {
        List<Mark> marks = markService.retrieveByTeacher(subjectId, id);
        return markMapper.entityToDto(marks);
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
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }

    @PutMapping
    public TeacherInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.dtoToEntity(teacherInfoDto);
        teacherInfo = teacherService.update(teacherInfo);
        teacherInfoDto = teacherInfoMapper.entityToDto(teacherInfo);
        return teacherInfoDto;
    }

    @PostMapping("/{id}/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubject(@PathVariable Long id,
                           @RequestParam Long subjectId) {
        teacherService.addSubjectForTeacher(id, subjectId);
    }

    @DeleteMapping("/{id}/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@PathVariable Long id,
                              @PathVariable Long subjectId) {
        teacherService.deleteSubjectForTeacher(id, subjectId);
    }

}

