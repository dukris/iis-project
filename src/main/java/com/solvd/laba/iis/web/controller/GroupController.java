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
import com.solvd.laba.iis.web.mapper.group.GroupMapper;
import com.solvd.laba.iis.web.mapper.lesson.LessonMapper;
import com.solvd.laba.iis.web.mapper.student.StudentInfoMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import com.solvd.laba.iis.web.mapper.lesson.LessonSearchCriteriaMapper;
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
    private final com.solvd.laba.iis.persistence.mybatis.GroupMapper groupMap;
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
//        List<Group> groups = groupService.retrieveAll();
        List<Group> groups = groupMap.findAll();
        List<GroupDto> groupDtos = groupMapper.entityToDto(groups);
        return groupDtos;
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable Long id) {
        Group group = groupService.retrieveById(id);
        GroupDto groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable Long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.dtoToEntity(lessonSearchCriteriaDto);
        List<Lesson> lessons = lessonService.retrieveByStudentCriteria(id, lessonSearchCriteria);
        List<LessonDto> lessonDtos = lessonMapper.entityToDto(lessons);
        return lessonDtos;
    }

    @GetMapping("/{id}/students")
    public List<StudentInfoDto> getStudents(@PathVariable Long id) {
        List<StudentInfo> students = studentService.retrieveByGroup(id);
        List<StudentInfoDto> studentDtos = studentInfoMapper.entityToDto(students);
        return studentDtos;
    }

    @GetMapping("/{id}/teachers")
    public List<TeacherInfoDto> getTeachers(@PathVariable Long id) {
        List<TeacherInfo> teachers = teacherService.retrieveByGroup(id);
        List<TeacherInfoDto> teacherDtos = teacherInfoMapper.entityToDto(teachers);
        return teacherDtos;
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
