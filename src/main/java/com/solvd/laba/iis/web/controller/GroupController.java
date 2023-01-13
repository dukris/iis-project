package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.group.Group;
import com.solvd.laba.iis.domain.lesson.LessonSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.group.GroupDto;
import com.solvd.laba.iis.web.dto.lesson.LessonDto;
import com.solvd.laba.iis.web.dto.student.StudentInfoDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.lesson.LessonSearchCriteriaDto;
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
        List<GroupDto> groups = groupMapper.entityToDto(groupService.findAll());
        return groups;
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable long id) {
        GroupDto group = groupMapper.entityToDto(groupService.findById(id));
        return group;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable long id,
                                      LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.dtoToEntity(lessonSearchCriteriaDto);
        List<LessonDto> lessons = lessonMapper.entityToDto(lessonService.findByStudentCriteria(id, lessonSearchCriteria));
        return lessons;
    }

    @GetMapping("/{id}/students")
    public List<StudentInfoDto> getStudents(@PathVariable long id) {
        List<StudentInfoDto> students = studentInfoMapper.entityToDto(studentService.findByGroup(id));
        return students;
    }

    @GetMapping("/{id}/teachers")
    public List<TeacherInfoDto> getTeachers(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherInfoMapper.entityToDto(teacherService.findByGroup(id));
        return teachers;
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
    public void delete(@PathVariable long id) {
        groupService.delete(id);
    }

    @PutMapping
    public GroupDto update(@RequestBody @Validated(OnUpdateGroup.class) GroupDto groupDto) {
        Group group = groupMapper.dtoToEntity(groupDto);
        group = groupService.save(group);
        groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

}
