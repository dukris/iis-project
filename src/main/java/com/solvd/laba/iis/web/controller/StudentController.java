package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.persistence.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.persistence.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.StudentInfoDto;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import com.solvd.laba.iis.web.mapper.StudentInfoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentInfoMapper studentInfoMapper;
    private final MarkService markService;
    private final MarkMapper markMapper;

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

    @GetMapping("/search") //?????????????????????????
    public List<StudentInfoDto> getByCriteria(StudentSearchCriteria studentSearchCriteria) {
        List<StudentInfoDto> students = studentService.getByCriteria(studentSearchCriteria).stream()
                .map(studentInfoMapper::studentInfoToStudentInfoDto)
                .toList();
        return students;
    }

    @GetMapping("/{id}/marks")
    public List<MarkDto> getMarks(@PathVariable long id,
                                  MarkSearchCriteria markSearchCriteria) {
        List<MarkDto> marks = markService.getByCriteria(id, markSearchCriteria).stream()
                        .map(markMapper::markToMarkDto)
                        .toList();
        return marks;
    }

//    @GetMapping("/speciality/{name}")
//    public List<StudentInfoDto> getBySpeciality(@PathVariable String name) {
//        List<StudentInfoDto> students = studentService.getBySpeciality(name).stream()
//                .map(studentInfoMapper::studentInfoToStudentInfoDto)
//                .toList();
//        return students;
//    }
//
//    @GetMapping("/faculty/{name}")
//    public List<StudentInfoDto> getByFaculty(@PathVariable String name) {
//        List<StudentInfoDto> students = studentService.getByFaculty(name).stream()
//                .map(studentInfoMapper::studentInfoToStudentInfoDto)
//                .toList();
//        return students;
//    }
//
//    @GetMapping("/year/{year}")
//    public List<StudentInfoDto> getByAdmissionYear(@PathVariable int year) {
//        List<StudentInfoDto> students = studentService.getByAdmissionYear(year).stream()
//                .map(studentInfoMapper::studentInfoToStudentInfoDto)
//                .toList();
//        return students;
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentInfoDto create(@RequestBody @Valid StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo =studentService.create(studentInfo);
        return studentInfoMapper.studentInfoToStudentInfoDto(studentInfo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentService.delete(studentInfo);
    }

    @PutMapping
    public StudentInfoDto update(@RequestBody @Valid StudentInfoDto studentInfoDto) {
        StudentInfo studentInfo = studentInfoMapper.studentInfoDtoToStudentInfo(studentInfoDto);
        studentInfo =studentService.save(studentInfo);
        return studentInfoMapper.studentInfoToStudentInfoDto(studentInfo);
    }
}
