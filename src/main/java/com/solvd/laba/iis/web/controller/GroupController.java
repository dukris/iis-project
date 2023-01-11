package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Group;
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
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
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
    private final GroupMapper groupMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private  final LessonSearchCriteriaMapper lessonSearchCriteriaMapper;
    private final StudentService studentService;
    private final StudentInfoMapper studentInfoMapper;
    private final TeacherService teacherService;
    private final TeacherInfoMapper teacherInfoMapper;

    @GetMapping
    public List<GroupDto> getAll() {
        List<GroupDto> groups = groupMapper.listToListDto(groupService.getAll());
        return groups;
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable long id) {
        GroupDto group = groupMapper.groupToGroupDto(groupService.getById(id));
        return group;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.lessonCriteriaDtoToLessonCriteria(lessonSearchCriteriaDto);
        List<LessonDto> lessons = lessonMapper.listToListDto(lessonService.getByStudentCriteria(id, lessonSearchCriteria));
        return lessons;
    }

    @GetMapping("/{id}/students")
    public List<StudentInfoDto> getStudents(@PathVariable long id) {
        List<StudentInfoDto> students = studentInfoMapper.listToListDto(studentService.getByGroup(id));
        return students;
    }

    @GetMapping("/{id}/teachers")
    public List<TeacherInfoDto> getTeachers(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherInfoMapper.listToListDto(teacherService.getByGroup(id));
        return teachers;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto create(@RequestBody @Validated(OnCreateGroup.class) GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.create(group);
        return groupMapper.groupToGroupDto(group);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        groupService.delete(group);
    }

    @PutMapping
    public GroupDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.save(group);
        return groupMapper.groupToGroupDto(group);
    }

}
