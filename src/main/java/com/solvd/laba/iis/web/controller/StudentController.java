package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentInfoMapper studentInfoMapper;

    @GetMapping
    public ResponseEntity<List<StudentInfoDto>> getAll() {
        List<StudentInfoDto> students = studentService.getAll().stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentInfoDto> getById(@PathVariable long id) {
        StudentInfoDto student = studentInfoMapper.studentInfoToStudentInfoDto(studentService.getById(id));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<StudentInfoDto>> getByGroup(@PathVariable long id) {
        List<StudentInfoDto> students = studentService.getByGroup(id).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/speciality/{name}")
    public ResponseEntity<List<StudentInfoDto>> getBySpeciality(@PathVariable String name) {
        List<StudentInfoDto> students = studentService.getBySpeciality(name).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/faculty/{name}")
    public ResponseEntity<List<StudentInfoDto>> getByFaculty(@PathVariable String name) {
        List<StudentInfoDto> students = studentService.getByFaculty(name).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<StudentInfoDto>> getByAdmissionYear(@PathVariable int year) {
        List<StudentInfoDto> students = studentService.getByAdmissionYear(year).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentInfoDto> create(@RequestBody @Valid StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo =studentService.create(studentInfo);
        return new ResponseEntity<>(studentInfoMapper.studentInfoToStudentInfoDto(studentInfo), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentService.delete(studentInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<StudentInfoDto> update(@RequestBody @Valid StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo =studentService.save(studentInfo);
        return new ResponseEntity<>(studentInfoMapper.studentInfoToStudentInfoDto(studentInfo), HttpStatus.OK);
    }
}
