package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.service.SubjectService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.SubjectDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import com.solvd.laba.iis.web.mapper.SubjectMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    private final TeacherService teacherService;
    private final TeacherInfoMapper teacherInfoMapper;

    @GetMapping
    public List<SubjectDto> getAll() {
        List<SubjectDto> subjects = subjectMapper.listToListDto(subjectService.getAll());
        return subjects;
    }

    @GetMapping("/{id}")
    public SubjectDto getById(@PathVariable long id) {
        SubjectDto subject = subjectMapper.subjectToSubjectDto(subjectService.getById(id));
        return subject;
    }

    @GetMapping("/{id}/teachers")
    public List<TeacherInfoDto> getTeachers(@PathVariable long id) {
        List<TeacherInfoDto> teachers = teacherInfoMapper.listToListDto(teacherService.getBySubject(id));
        return teachers;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@RequestBody @Validated(OnCreateGroup.class) SubjectDto subjectDto) {
        Subject subject = subjectMapper.subjectDtoToSubject(subjectDto);
        subject = subjectService.create(subject);
        return subjectMapper.subjectToSubjectDto(subject);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) SubjectDto subjectDto) {
        Subject subject = subjectMapper.subjectDtoToSubject(subjectDto);
        subjectService.delete(subject);
    }

    @PutMapping
    public SubjectDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) SubjectDto subjectDto) {
        Subject subject = subjectMapper.subjectDtoToSubject(subjectDto);
        subject = subjectService.save(subject);
        return subjectMapper.subjectToSubjectDto(subject);
    }

}
