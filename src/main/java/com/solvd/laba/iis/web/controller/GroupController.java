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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
@Tag(name = "Group Controller", description = "Methods for working with groups")
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
    @Operation(summary = "Get all groups")
    public List<GroupDto> getAll() {
        List<Group> groups = groupService.retrieveAll();
        List<GroupDto> groupDtos = groupMapper.entityToDto(groups);
        return groupDtos;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by id")
    public GroupDto getById(@PathVariable @Parameter(description = "Group's id") Long id) {
        Group group = groupService.retrieveById(id);
        GroupDto groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

    @GetMapping("/{id}/lessons")
    @Operation(summary = "Get lessons for group")
    public List<LessonDto> getLessons(@PathVariable @Parameter(description = "Group's id") Long id,
                                      @Parameter(description = "Criteria for searching lessons") LessonSearchCriteriaDto lessonSearchCriteriaDto) {
        LessonSearchCriteria lessonSearchCriteria = lessonSearchCriteriaMapper.dtoToEntity(lessonSearchCriteriaDto);
        List<Lesson> lessons = lessonService.retrieveByStudentCriteria(id, lessonSearchCriteria);
        List<LessonDto> lessonDtos = lessonMapper.entityToDto(lessons);
        return lessonDtos;
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get students by group")
    public List<StudentInfoDto> getStudents(@PathVariable @Parameter(description = "Group's id") Long id) {
        List<StudentInfo> students = studentService.retrieveByGroup(id);
        List<StudentInfoDto> studentDtos = studentInfoMapper.entityToDto(students);
        return studentDtos;
    }

    @GetMapping("/{id}/teachers")
    @Operation(summary = "Get teachers by group")
    public List<TeacherInfoDto> getTeachers(@PathVariable @Parameter(description = "Group's id") Long id) {
        List<TeacherInfo> teachers = teacherService.retrieveByGroup(id);
        List<TeacherInfoDto> teacherDtos = teacherInfoMapper.entityToDto(teachers);
        return teacherDtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new group")
    public GroupDto create(@RequestBody @Validated(OnCreateGroup.class) @Parameter(description = "Information about group") GroupDto groupDto) {
        Group group = groupMapper.dtoToEntity(groupDto);
        group = groupService.create(group);
        groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete group")
    public void delete(@PathVariable @Parameter(description = "Group's id") Long id) {
        groupService.delete(id);
    }

    @PutMapping
    @Operation(summary = "Update information about group")
    public GroupDto update(@RequestBody @Validated(OnUpdateGroup.class) @Parameter(description = "Information about group") GroupDto groupDto) {
        Group group = groupMapper.dtoToEntity(groupDto);
        group = groupService.update(group);
        groupDto = groupMapper.entityToDto(group);
        return groupDto;
    }

}
