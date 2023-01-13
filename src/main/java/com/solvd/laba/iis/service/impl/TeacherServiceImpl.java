package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.TeacherRepository;
import com.solvd.laba.iis.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherInfo> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public TeacherInfo findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Teacher with id = " + id + " not found"));
    }

    @Override
    public List<TeacherInfo> findByGroup(Long groupId) {
        return teacherRepository.findByGroup(groupId);
    }

    @Override
    public List<TeacherInfo> findBySubject(Long subjectId) {
        return teacherRepository.findBySubject(subjectId);
    }

    @Override
    public TeacherInfo create(TeacherInfo teacherInfo) {
        teacherRepository.create(teacherInfo);
        return teacherInfo;
    }

    @Override
    public TeacherInfo save(TeacherInfo teacherInfo) {
        findById(teacherInfo.getId());
        teacherRepository.save(teacherInfo);
        return teacherInfo;
    }

    @Override
    public void delete(Long id) {
        teacherRepository.delete(id);
    }

    @Override
    public void deleteSubjectForTeacher(Long teacherId, Long subjectId) {
        teacherRepository.deleteSubject(teacherId, subjectId);
    }

    @Override
    public void addSubjectForTeacher(Long teacherId, Long subjectId) {
        teacherRepository.addSubject(teacherId, subjectId);
    }

}
