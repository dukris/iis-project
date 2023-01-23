package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.GroupDto;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.criteria.LessonSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import com.solvd.laba.iis.web.mapper.criteria.LessonSearchCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    private final LessonService lessonService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GroupMapper groupMapper;
    private final LessonMapper lessonMapper;
    private final LessonSearchCriteriaMapper lessonSearchCriteriaMapper;
    private final StudentInfoMapper studentInfoMapper;
    private final TeacherInfoMapper teacherInfoMapper;

    @GetMapping
    public List<GroupDto> getAll() {
        List<Group> groups = groupService.retrieveAll();
        return groupMapper.entityToDto(groups);
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable Long id) {
        Group group = groupService.retrieveById(id);
        return groupMapper.entityToDto(group);
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable Long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.dtoToEntity(lessonSearchCriteriaDto);
        List<Lesson> lessons = lessonService.retrieveByStudentCriteria(id, lessonSearchCriteria);
        return lessonMapper.entityToDto(lessons);
    }

    @GetMapping("/{id}/students")
    public List<StudentInfoDto> getStudents(@PathVariable Long id) {
        List<StudentInfo> students = studentService.retrieveByGroup(id);
        return studentInfoMapper.entityToDto(students);
    }

    @GetMapping("/{id}/teachers")
    public List<TeacherInfoDto> getTeachers(@PathVariable Long id) {
        List<TeacherInfo> teachers = teacherService.retrieveByGroup(id);
        return teacherInfoMapper.entityToDto(teachers);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto create(@RequestBody @Validated(OnCreateGroup.class) GroupDto groupDto) {
        Group group = groupMapper.dtoToEntity(groupDto);
        group = groupService.create(group);
        groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @PutMapping
    public GroupDto update(@RequestBody @Validated(OnUpdateGroup.class) GroupDto groupDto) {
        Group group = groupMapper.dtoToEntity(groupDto);
        group = groupService.update(group);
        groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

}
