package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.student.StudentInfo;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.StudentRepository;
import com.solvd.laba.iis.domain.student.StudentSearchCriteria;
import com.solvd.laba.iis.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentInfo> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentInfo findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Student with id = " + id + " not found"));
    }

    @Override
    public List<StudentInfo> findByGroup(Long groupId) {
        return studentRepository.findByGroup(groupId);
    }

    @Override
    public List<StudentInfo> findByCriteria(StudentSearchCriteria studentSearchCriteria) {
        return studentRepository.findByCriteria(studentSearchCriteria);
    }

    @Override
    public StudentInfo create(StudentInfo studentInfo) {
        studentRepository.create(studentInfo);
        return studentInfo;
    }

    @Override
    public StudentInfo save(StudentInfo studentInfo) {
        findById(studentInfo.getId());
        studentRepository.save(studentInfo);
        return studentInfo;
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

}
