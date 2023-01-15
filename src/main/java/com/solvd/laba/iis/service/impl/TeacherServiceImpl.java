package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.jdbc.TeacherRepository;
import com.solvd.laba.iis.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherInfo> retrieveAll() {
        return teacherRepository.findAll();
    }

    @Override
    public TeacherInfo retrieveById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("Teacher with id = " + id + " not found"));
    }

    @Override
    public List<TeacherInfo> retrieveByGroup(Long groupId) {
        return teacherRepository.findByGroup(groupId);
    }

    @Override
    public List<TeacherInfo> retrieveBySubject(Long subjectId) {
        return teacherRepository.findBySubject(subjectId);
    }

    @Override
    @Transactional
    public TeacherInfo create(TeacherInfo teacherInfo) {
        teacherRepository.create(teacherInfo);
        return teacherInfo;
    }

    @Override
    @Transactional
    public TeacherInfo update(TeacherInfo teacherInfo) {
        TeacherInfo foundTeacher = retrieveById(teacherInfo.getId());
        foundTeacher.setUser(teacherInfo.getUser());
        foundTeacher.setSubjects(teacherInfo.getSubjects());
        teacherRepository.update(teacherInfo);
        return teacherInfo;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        teacherRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteSubjectForTeacher(Long teacherId, Long subjectId) {
        teacherRepository.deleteSubject(teacherId, subjectId);
    }

    @Override
    @Transactional
    public void addSubjectForTeacher(Long teacherId, Long subjectId) {
        teacherRepository.addSubject(teacherId, subjectId);
    }

}
