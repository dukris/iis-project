package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentInfoMapper studentInfoMapper;

    @GetMapping
    public List<StudentInfoDto> getAll() {
        List<StudentInfoDto> students = studentService.getAll().stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @GetMapping("/{id}")
    public StudentInfoDto getById(@PathVariable long id) {
        StudentInfoDto student = studentInfoMapper.studentInfoToStudentInfoDto(studentService.getById(id));
        return student;
    }

    @GetMapping("/group/{id}")
    public List<StudentInfoDto> getByGroup(@PathVariable long id) {
        List<StudentInfoDto> students = studentService.getByGroup(id).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @GetMapping("/speciality/{name}")
    public List<StudentInfoDto> getBySpeciality(@PathVariable String name) {
        List<StudentInfoDto> students = studentService.getBySpeciality(name).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @GetMapping("/faculty/{name}")
    public List<StudentInfoDto> getByFaculty(@PathVariable String name) {
        List<StudentInfoDto> students = studentService.getByFaculty(name).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @GetMapping("/year/{year}")
    public List<StudentInfoDto> getByAdmissionYear(@PathVariable int year) {
        List<StudentInfoDto> students = studentService.getByAdmissionYear(year).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @PostMapping
    public StudentInfoDto create(@RequestBody StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo =studentService.create(studentInfo);
        return studentInfoMapper.studentInfoToStudentInfoDto(studentInfo);
    }

    @DeleteMapping
    public void delete(@RequestBody StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentService.delete(studentInfo);
    }

    @PutMapping
    public StudentInfoDto update(@RequestBody StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo =studentService.save(studentInfo);
        return studentInfoMapper.studentInfoToStudentInfoDto(studentInfo);
    }
}
