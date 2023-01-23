package com.solvd.laba.iis.web.security;

import com.solvd.laba.iis.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        try {
            String token = getTokenFromRequest((HttpServletRequest) request);
            if (token != null) {
                Claims claims = jwtService.parseToken(token);
                JwtUser jwtUser = JwtUserFactory.create(claims);
                Authentication authentication = jwtService.getAuthenticationForUser(jwtUser);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            fc.doFilter(request, response);
        } catch (JwtException | NullPointerException ex) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
