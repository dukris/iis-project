package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.exception.ResourceNotFoundException;
import com.solvd.laba.iis.persistence.StudentRepository;
import com.solvd.laba.iis.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<StudentInfo> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentInfo getById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id = " + id + "not found"));
    }

    @Override
    public List<StudentInfo> getByGroup(long groupId) {
        return studentRepository.findByGroup(groupId);
    }

    @Override
    public List<StudentInfo> getBySpeciality(String speciality) {
        return studentRepository.findBySpeciality(speciality);
    }

    @Override
    public List<StudentInfo> getByFaculty(String faculty) {
        return studentRepository.findByFaculty(faculty);
    }

    @Override
    public List<StudentInfo> getByAdmissionYear(int year) {
        return studentRepository.findByAdmissionYear(year);
    }

    @Override
    public StudentInfo create(StudentInfo studentInfo) {
        return studentRepository.create(studentInfo);
    }

    @Override
    public StudentInfo save(StudentInfo studentInfo) {
        return studentRepository.save(studentInfo);
    }

    @Override
    public void delete(StudentInfo studentInfo) {
        studentRepository.delete(studentInfo);
    }
}
