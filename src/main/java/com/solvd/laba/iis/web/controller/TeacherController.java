package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherInfoMapper teacherInfoMapper;

    @GetMapping
    public ResponseEntity<List<TeacherInfoDto>> getAll() {
        List<TeacherInfoDto> teachers = teacherService.getAll().stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherInfoDto> getById(@PathVariable long id) {
        TeacherInfoDto teacher = teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherService.getById(id));
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<TeacherInfoDto>> getByGroup(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherService.getByGroup(id).stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<List<TeacherInfoDto>> getBySubject(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherService.getBySubject(id).stream()
                .map(teacherInfoMapper::teacherInfoToTeacherInfoDto)
                .toList();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeacherInfoDto> create(@RequestBody @Valid TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.create(teacherInfo);
        return new ResponseEntity<>(teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherService.delete(teacherInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<TeacherInfoDto> update(@RequestBody @Valid TeacherInfoDto teacherInfoDto) {
        TeacherInfo teacherInfo = teacherInfoMapper.teacherInfoDtoToTeacherInfo(teacherInfoDto);
        teacherInfo = teacherService.save(teacherInfo);
        return new ResponseEntity<>(teacherInfoMapper.teacherInfoToTeacherInfoDto(teacherInfo), HttpStatus.OK);
    }

    @PostMapping("/subject")
    public ResponseEntity<Void> addSubject(@RequestParam long teacherId,
                           @RequestParam long subjectId) {
        teacherService.addSubject(teacherId, subjectId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/subject")
    public ResponseEntity<Void> deleteSubject(@RequestParam long teacherId,
                              @RequestParam long subjectId) {
        teacherService.deleteSubject(teacherId, subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

