package com.solvd.laba.iis.web.controller;

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
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import com.solvd.laba.iis.web.mapper.criteria.MarkSearchCriteriaMapper;
import com.solvd.laba.iis.web.mapper.criteria.StudentSearchCriteriaMapper;
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
    private final StudentInfoMapper studentInfoMapper;
    private final StudentSearchCriteriaMapper studentSearchCriteriaMapper;
    private final MarkService markService;
    private final MarkMapper markMapper;
    private final MarkSearchCriteriaMapper markSearchCriteriaMapper;

    @GetMapping
    public List<StudentInfoDto> getAll() {
        List<StudentInfoDto> students = studentInfoMapper.listToListDto(studentService.getAll());
        return students;
    }

    @GetMapping("/{id}")
    public StudentInfoDto getById(@PathVariable long id) {
        StudentInfoDto student = studentInfoMapper.studentInfoToStudentInfoDto(studentService.getById(id));
        return student;
    }

    @GetMapping("/search")
    public List<StudentInfoDto> getByCriteria(StudentSearchCriteriaDto studentSearchCriteriaDto) {
        StudentSearchCriteria studentSearchCriteria = studentSearchCriteriaMapper.studentCriteriaDtoToStudentCriteria(studentSearchCriteriaDto);
        List<StudentInfoDto> students = studentInfoMapper.listToListDto(studentService.getByCriteria(studentSearchCriteria));
        return students;
    }

    @GetMapping("/{id}/marks")
    public List<MarkDto> getMarks(@PathVariable long id,
                                  MarkSearchCriteriaDto markSearchCriteriaDto) {
        MarkSearchCriteria markSearchCriteria = markSearchCriteriaMapper.markCriteriaDtoToMarkCriteria(markSearchCriteriaDto);
        List<MarkDto> marks = markMapper.listToListDto(markService.getByCriteria(id, markSearchCriteria));
        return marks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentInfoDto create(@RequestBody @Validated(OnCreateStudentGroup.class) StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo = studentService.create(studentInfo);
        return studentInfoMapper.studentInfoToStudentInfoDto(studentInfo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        studentService.delete(id);
    }

    @PutMapping
    public StudentInfoDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo = studentService.save(studentInfo);
        return studentInfoMapper.studentInfoToStudentInfoDto(studentInfo);
    }

}
