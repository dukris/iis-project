package com.solvd.laba.iis.web.security.expression;

import com.solvd.laba.iis.domain.*;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.service.SubjectService;
import com.solvd.laba.iis.service.TeacherService;
import com.solvd.laba.iis.web.security.JwtUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private SubjectService subjectService;
    private StudentService studentService;
    private TeacherService teacherService;
    private GroupService groupService;
    private HttpServletRequest request;
    private Object filterObject;
    private Object returnObject;
    private Object target;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasAccessForTeacher(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        Long userId = jwtUser.getId();
        TeacherInfo teacherInfo = teacherService.retrieveByUserId(userId);
        return id.equals(teacherInfo.getId()) || isAdmin(authentication);
    }

    public boolean hasAccessForStudent(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        Long userId = jwtUser.getId();
        StudentInfo studentInfo = studentService.retrieveByUserId(userId);
        return id.equals(studentInfo.getId()) || isAdmin(authentication);
    }

    public boolean hasAccessToSubject(Long teacherId, Long subjectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        Long userId = jwtUser.getId();
        TeacherInfo teacherInfo = teacherService.retrieveByUserId(userId);
        if (isAdmin(authentication)) {
            return true;
        }
        if (!teacherId.equals(teacherInfo.getId())) {
            return false;
        }
        List<Subject> subjects = subjectService.retrieveByTeacher(teacherId);
        return subjects.stream()
                .anyMatch(subject -> subjectId.equals(subject.getId()));
    }

    public boolean hasAccessToMark(Long teacherId, Long studentId, Long subjectId) {
        if (!hasAccessToSubject(teacherId, subjectId)) {
            return false;
        }
        List<Group> groups = groupService.retrieveByTeacherAndSubject(teacherId, subjectId);
        return groups.stream()
                .map(Group::getId)
                .flatMap(groupId -> studentService.retrieveByGroup(groupId).stream())
                .anyMatch(student -> student.getId().equals(studentId));
    }

    private boolean isAdmin(Authentication authentication) {
        GrantedAuthority role = new SimpleGrantedAuthority(UserInfo.Role.ROLE_ADMIN.name());
        return authentication.getAuthorities().contains(role);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}