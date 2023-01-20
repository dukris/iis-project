package com.solvd.laba.iis.web;

import com.solvd.laba.iis.web.security.JwtFilter;
import com.solvd.laba.iis.web.security.expression.CustomMethodSecurityExpressionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final ApplicationContext applicationContext;
    private final String[] teacherGetRequests = {"/api/v1/teachers/**"};
    private final String[] teacherOtherRequests = {"/api/v1/marks", "/api/v1/marks/*"};
    private final String[] studentGetRequests = {"/api/v1/students/**"};
    private final String[] commonGetRequests = {"/api/v1/lessons/**", "/api/v1/marks/**", "/api/v1/groups/**", "/api/v1/subjects/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Bad credentials");
                }))
                .accessDeniedHandler((request, response, exception) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Access is denied");
                })
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, commonGetRequests).authenticated()
                .requestMatchers(HttpMethod.GET, teacherGetRequests).hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(teacherOtherRequests).hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.GET, studentGetRequests).hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers("/api/v1/**").hasRole("ADMIN")
                .and()
                .addFilterAfter(jwtFilter, ExceptionTranslationFilter.class)
                .build();
    }

    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {
        var expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

}
