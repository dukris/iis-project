package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.StudentRepository;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentInfo> retrieveAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentInfo retrieveById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Student with id = " + id + " not found"));
    }

    @Override
    public List<StudentInfo> retrieveByGroup(Long groupId) {
        return studentRepository.findByGroup(groupId);
    }

    @Override
    public List<StudentInfo> retrieveByCriteria(StudentSearchCriteria studentSearchCriteria) {
        return studentRepository.findByCriteria(studentSearchCriteria);
    }

    @Override
    @Transactional
    public StudentInfo create(StudentInfo studentInfo) {
        studentRepository.create(studentInfo);
        return studentInfo;
    }

    @Override
    @Transactional
    public StudentInfo update(StudentInfo studentInfo) {
        StudentInfo foundStudent = retrieveById(studentInfo.getId());
        foundStudent.setAdmissionYear(studentInfo.getAdmissionYear());
        foundStudent.setFaculty(studentInfo.getFaculty());
        foundStudent.setSpeciality(studentInfo.getSpeciality());
        foundStudent.setUser(studentInfo.getUser());
        foundStudent.setGroup(studentInfo.getGroup());
        studentRepository.update(studentInfo);
        return studentInfo;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentRepository.delete(id);
    }

}
