package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherInfoMapper teacherInfoMapper;

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

    @GetMapping("/group/{id}")
    public List<TeacherInfoDto> getByGroup(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherService.getByGroup(id).stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return teachers;
    }

    @GetMapping("/subject/{id}")
    public List<TeacherInfoDto> getBySubject(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherService.getBySubject(id).stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return teachers;
    }

    @PostMapping
    public TeacherInfoDto create(@RequestBody TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.create(teacherInfo);
        return teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo);
    }

    @DeleteMapping
    public void delete(@RequestBody TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherService.delete(teacherInfo);
    }

    @PutMapping
    public TeacherInfoDto update(@RequestBody TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.save(teacherInfo);
        return teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo);
    }

    @PostMapping("/subject")
    public void addSubject(@RequestParam long teacherId,
                           @RequestParam long subjectId) {
        teacherService.addSubject(teacherId, subjectId);
    }

    @DeleteMapping("/subject")
    public void deleteSubject(@RequestParam long teacherId,
                              @RequestParam long subjectId) {
        teacherService.deleteSubject(teacherId, subjectId);
    }
}

