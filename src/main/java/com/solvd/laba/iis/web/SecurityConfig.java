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

    private static final String[] PERMITTED_REQUESTS = {"/api/v1/users/login", "/api/v1/users/refresh", "/api/v1/swagger-ui.html", "/api/v1/swagger-ui/*", "/api/v1/swagger-ui/**"};
    private static final String[] COMMON_GET_REQUESTS = {"/api/v1/lessons/**", "/api/v1/marks/**", "/api/v1/groups/**", "/api/v1/subjects/**"};
    private static final String[] TEACHER_GET_REQUESTS = {"/api/v1/teachers/**"};
    private static final String[] TEACHER_OTHER_REQUESTS = {"/api/v1/marks", "/api/v1/marks/*"};
    private static final String[] STUDENT_GET_REQUESTS = {"/api/v1/students/**"};
    private static final String[] ADMIN_REQUESTS = {"/api/v1/**"};

    private final JwtFilter jwtFilter;
    private final ApplicationContext applicationContext;

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
//                .requestMatchers(PERMITTED_REQUESTS).permitAll()
                .requestMatchers(HttpMethod.GET, COMMON_GET_REQUESTS).authenticated()
                .requestMatchers(HttpMethod.GET, TEACHER_GET_REQUESTS).hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(TEACHER_OTHER_REQUESTS).hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.GET, STUDENT_GET_REQUESTS).hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers(ADMIN_REQUESTS).hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .addFilterAfter(jwtFilter, ExceptionTranslationFilter.class)
                .build();
    }

    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

}
