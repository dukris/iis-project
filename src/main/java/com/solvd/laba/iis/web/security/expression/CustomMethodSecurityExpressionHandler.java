package com.solvd.laba.iis.web.security.expression;

import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.service.StudentService;
import com.solvd.laba.iis.service.SubjectService;
import com.solvd.laba.iis.service.TeacherService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private ApplicationContext applicationContext;
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {

        CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(authentication);
        root.setTrustResolver(this.trustResolver);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setSubjectService(this.applicationContext.getBean(SubjectService.class));
        root.setStudentService(this.applicationContext.getBean(StudentService.class));
        root.setGroupService(this.applicationContext.getBean(GroupService.class));
        root.setTeacherService(this.applicationContext.getBean(TeacherService.class));
        return root;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

}
