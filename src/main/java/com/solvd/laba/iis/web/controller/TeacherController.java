package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.persistence.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.persistence.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.GroupDto;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherInfoMapper teacherInfoMapper;
    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping
    public List<TeacherInfoDto> getAll() {
        List<TeacherInfoDto> teachers = teacherService.getAll().stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return teachers;
    }

    @GetMapping("/{id}")
    public TeacherInfoDto getById(@PathVariable long id) {
        TeacherInfoDto teacher = teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherService.getById(id));
        return teacher;
    }

    @GetMapping("/{id}/groups")
    public List<GroupDto> getGroups(@PathVariable long id,
                                    GroupSearchCriteria groupSearchCriteria) {
        List<GroupDto> groups = groupService.getByCriteria(id, groupSearchCriteria).stream()
                .map(groupMapper::groupToGroupDto)
                .toList();
        return groups;
    }

    @GetMapping("/{id}/lessons")
    public List<LessonDto> getLessons(@PathVariable long id) {
        List<LessonDto> lessons = lessonService.getByTeacher(id).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList();
        return lessons;
    }

    @GetMapping("/{id}/subjects/{subjectId}/marks")
    public List<MarkDto> getMarks(@PathVariable long id,
                                  @PathVariable long subjectId,
                                  MarkSearchCriteria markSearchCriteria) {
        List<MarkDto> marks = markService.getByTeacher(subjectId, id).stream()
                .map(markMapper::markToMarkDto)
                .toList();
        return marks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherInfoDto create(@RequestBody @Valid TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.create(teacherInfo);
        return teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherService.delete(teacherInfo);
    }

    @PutMapping
    public TeacherInfoDto update(@RequestBody @Valid TeacherInfoDto teacherInfoDto) {
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

