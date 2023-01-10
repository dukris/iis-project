package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.persistence.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.GroupDto;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final StudentService studentService;
    private final StudentInfoMapper studentInfoMapper;
    private final TeacherService teacherService;
    private final TeacherInfoMapper teacherInfoMapper;

    @GetMapping
    public List<GroupDto> getAll() {
        List<GroupDto> groups = groupService.getAll().stream()
                .map(groupMapper::groupToGroupDto)
                .toList();
        return groups;
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable long id) {
        GroupDto group = groupMapper.groupToGroupDto(groupService.getById(id));
        return group;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable long id,
                                      LessonSearchCriteria lessonSearchCriteria) {
        List<LessonDto> lessons = lessonService.getByCriteria(id, lessonSearchCriteria).stream()
                .map(lessonMapper::lessonToLessonDto)
                .toList();
        return lessons;
    }

    @GetMapping("/{id}/students")
    public List<StudentInfoDto> getStudents(@PathVariable long id) {
        List<StudentInfoDto> students = studentService.getByGroup(id).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @GetMapping("/{id}/teachers")
    public List<TeacherInfoDto> getTeachers(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherService.getByGroup(id).stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return teachers;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto create(@RequestBody @Valid GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.create(group);
        return groupMapper.groupToGroupDto(group);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        groupService.delete(group);
    }

    @PutMapping
    public GroupDto update(@RequestBody @Valid GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.save(group);
        return groupMapper.groupToGroupDto(group);
    }
}
