package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.service.SubjectService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.dto.SubjectDto;
import com.solvd.laba.iis.web.dto.TeacherInfoDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.SubjectMapper;
import com.solvd.laba.iis.web.mapper.TeacherInfoMapper;
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
@RequestMapping("/api/v1/subjects")
@Tag(name = "Subject Controller", description = "Methods for working with subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final SubjectMapper subjectMapper;
    private final TeacherInfoMapper teacherInfoMapper;

    @GetMapping
    @Operation(summary = "Get all subjects")
    public List<SubjectDto> getAll() {
        List<Subject> subjects = subjectService.retrieveAll();
        return subjectMapper.entityToDto(subjects);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subject by id")
    public SubjectDto getById(@PathVariable @Parameter(description = "Subject's id") Long id) {
        Subject subject = subjectService.retrieveById(id);
        return subjectMapper.entityToDto(subject);
    }

    @GetMapping("/{id}/teachers")
    @Operation(summary = "Get teachers by subject")
    public List<TeacherInfoDto> getTeachers(@PathVariable @Parameter(description = "Subject's id") Long id) {
        List<TeacherInfo> teachers = teacherService.retrieveBySubject(id);
        return teacherInfoMapper.entityToDto(teachers);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new subject")
    public SubjectDto create(@RequestBody @Validated(OnCreateGroup.class) @Parameter(description = "Information about subject") SubjectDto subjectDto) {
        Subject subject = subjectMapper.dtoToEntity(subjectDto);
        subject = subjectService.create(subject);
        subjectDto = subjectMapper.entityToDto(subject);
        return subjectDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete subject")
    public void delete(@PathVariable @Parameter(description = "Subject's id") Long id) {
        subjectService.delete(id);
    }

    @PutMapping
    @Operation(summary = "Update information about subject")
    public SubjectDto update(@RequestBody @Validated(OnUpdateGroup.class) @Parameter(description = "Information about subject") SubjectDto subjectDto) {
        Subject subject = subjectMapper.dtoToEntity(subjectDto);
        subject = subjectService.update(subject);
        subjectDto = subjectMapper.entityToDto(subject);
        return subjectDto;
    }

}
