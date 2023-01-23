package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import com.solvd.laba.iis.web.dto.criteria.MarkSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.criteria.StudentSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import com.solvd.laba.iis.web.mapper.criteria.MarkSearchCriteriaMapper;
import com.solvd.laba.iis.web.mapper.criteria.StudentSearchCriteriaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
@Tag(name = "Student Controller", description = "Methods for working with students")
public class StudentController {

    private final StudentService studentService;
    private final MarkService markService;
    private final StudentInfoMapper studentInfoMapper;
    private final StudentSearchCriteriaMapper studentSearchCriteriaMapper;
    private final MarkMapper markMapper;
    private final MarkSearchCriteriaMapper markSearchCriteriaMapper;

    @GetMapping
    @Operation(summary = "Get all students")
    public List<StudentInfoDto> getAll() {
        List<StudentInfo> students = studentService.retrieveAll();
        List<StudentInfoDto> studentDtos = studentInfoMapper.entityToDto(students);
        return studentDtos;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAccessForStudent(#id)")
    @Operation(summary = "Get student by id")
    public StudentInfoDto getById(@PathVariable @Parameter(description = "Student's id") Long id) {
        StudentInfo student = studentService.retrieveById(id);
        StudentInfoDto studentDto = studentInfoMapper.entityToDto(student);
        return studentDto;
    }

    @GetMapping("/search")
    @Operation(summary = "Get students by criteria")
    public List<StudentInfoDto> getByCriteria(@Parameter(description = "Criteria for searching students") StudentSearchCriteriaDto studentSearchCriteriaDto) {
        StudentSearchCriteria studentSearchCriteria = studentSearchCriteriaMapper.dtoToEntity(studentSearchCriteriaDto);
        List<StudentInfo> students = studentService.retrieveByCriteria(studentSearchCriteria);
        List<StudentInfoDto> studentDtos = studentInfoMapper.entityToDto(students);
        return studentDtos;
    }

    @GetMapping("/{id}/marks")
    @PreAuthorize("hasAccessForStudent(#id)")
    @Operation(summary = "Get marks for student")
    public List<MarkDto> getMarks(@PathVariable @Parameter(description = "Student's id") Long id,
                                  @Parameter(description = "Criteria for searching marks") MarkSearchCriteriaDto markSearchCriteriaDto) {
        MarkSearchCriteria markSearchCriteria = markSearchCriteriaMapper.dtoToEntity(markSearchCriteriaDto);
        List<Mark> marks = markService.retrieveByCriteria(id, markSearchCriteria);
        List<MarkDto> markDtos = markMapper.entityToDto(marks);
        return markDtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new student")
    public StudentInfoDto create(@RequestBody @Validated(OnCreateStudentGroup.class) @Parameter(description = "Information about student") StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.dtoToEntity(studentInfoDto);
        studentInfo = studentService.create(studentInfo);
        studentInfoDto = studentInfoMapper.entityToDto(studentInfo);
        return studentInfoDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete student")
    public void delete(@PathVariable @Parameter(description = "Student's id") Long id) {
        studentService.delete(id);
    }

    @PutMapping
    @Operation(summary = "Update information about student")
    public StudentInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) @Parameter(description = "Information about student") StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.dtoToEntity(studentInfoDto);
        studentInfo = studentService.update(studentInfo);
        studentInfoDto = studentInfoMapper.entityToDto(studentInfo);
        return studentInfoDto;
    }

}
