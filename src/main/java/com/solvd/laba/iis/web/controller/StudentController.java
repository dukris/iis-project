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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final MarkService markService;
    private final StudentInfoMapper studentInfoMapper;
    private final StudentSearchCriteriaMapper studentSearchCriteriaMapper;
    private final MarkMapper markMapper;
    private final MarkSearchCriteriaMapper markSearchCriteriaMapper;

    @GetMapping
    public List<StudentInfoDto> getAll() {
        List<StudentInfo> students = studentService.retrieveAll();
        return studentInfoMapper.entityToDto(students);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAccessForStudent(#id)")
    public StudentInfoDto getById(@PathVariable Long id) {
        StudentInfo student = studentService.retrieveById(id);
        return studentInfoMapper.entityToDto(student);
    }

    @GetMapping("/search")
    public List<StudentInfoDto> getByCriteria(StudentSearchCriteriaDto studentSearchCriteriaDto) {
        StudentSearchCriteria studentSearchCriteria = studentSearchCriteriaMapper.dtoToEntity(studentSearchCriteriaDto);
        List<StudentInfo> students = studentService.retrieveByCriteria(studentSearchCriteria);
        return studentInfoMapper.entityToDto(students);
    }

    @GetMapping("/{id}/marks")
    @PreAuthorize("hasAccessForStudent(#id)")
    public List<MarkDto> getMarks(@PathVariable Long id,
                                  MarkSearchCriteriaDto markSearchCriteriaDto) {
        MarkSearchCriteria markSearchCriteria = markSearchCriteriaMapper.dtoToEntity(markSearchCriteriaDto);
        List<Mark> marks = markService.retrieveByCriteria(id, markSearchCriteria);
        return markMapper.entityToDto(marks);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentInfoDto create(@RequestBody @Validated(OnCreateStudentGroup.class) StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.dtoToEntity(studentInfoDto);
        studentInfo = studentService.create(studentInfo);
        studentInfoDto = studentInfoMapper.entityToDto(studentInfo);
        return studentInfoDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }

    @PutMapping
    public StudentInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.dtoToEntity(studentInfoDto);
        studentInfo = studentService.update(studentInfo);
        studentInfoDto = studentInfoMapper.entityToDto(studentInfo);
        return studentInfoDto;
    }

}
