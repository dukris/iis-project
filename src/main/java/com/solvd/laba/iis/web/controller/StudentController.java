package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.student.StudentInfo;
import com.solvd.laba.iis.domain.mark.MarkSearchCriteria;
import com.solvd.laba.iis.domain.student.StudentSearchCriteria;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.web.dto.mark.MarkDto;
import com.solvd.laba.iis.web.dto.student.StudentInfoDto;
import com.solvd.laba.iis.web.dto.mark.MarkSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.student.StudentSearchCriteriaDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.mark.MarkMapper;
import com.solvd.laba.iis.web.mapper.student.StudentInfoMapper;
import com.solvd.laba.iis.web.mapper.mark.MarkSearchCriteriaMapper;
import com.solvd.laba.iis.web.mapper.student.StudentSearchCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        List<StudentInfoDto> students = studentInfoMapper.entityToDto(studentService.findAll());
        return students;
    }

    @GetMapping("/{id}")
    public StudentInfoDto getById(@PathVariable long id) {
        StudentInfoDto student = studentInfoMapper.entityToDto(studentService.findById(id));
        return student;
    }

    @GetMapping("/search")
    public List<StudentInfoDto> getByCriteria(StudentSearchCriteriaDto studentSearchCriteriaDto) {
        StudentSearchCriteria studentSearchCriteria = studentSearchCriteriaMapper.dtoToEntity(studentSearchCriteriaDto);
        List<StudentInfoDto> students = studentInfoMapper.entityToDto(studentService.findByCriteria(studentSearchCriteria));
        return students;
    }

    @GetMapping("/{id}/marks")
    public List<MarkDto> getMarks(@PathVariable long id,
                                  MarkSearchCriteriaDto markSearchCriteriaDto) {
        MarkSearchCriteria markSearchCriteria = markSearchCriteriaMapper.dtoToEntity(markSearchCriteriaDto);
        List<MarkDto> marks = markMapper.entityToDto(markService.findByCriteria(id, markSearchCriteria));
        return marks;
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
    public void delete(@PathVariable long id) {
        studentService.delete(id);
    }

    @PutMapping
    public StudentInfoDto update(@RequestBody @Validated(OnUpdateGroup.class) StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.dtoToEntity(studentInfoDto);
        studentInfo = studentService.save(studentInfo);
        studentInfoDto = studentInfoMapper.entityToDto(studentInfo);
        return studentInfoDto;
    }

}
