package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.AuthenticationException;
import com.solvd.laba.iis.domain.security.JwtRefreshRequest;
import com.solvd.laba.iis.domain.security.JwtRequest;
import com.solvd.laba.iis.domain.security.JwtResponse;
import com.solvd.laba.iis.service.JwtService;
import com.solvd.laba.iis.service.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void verifyLoginSuccessTest() {
        UserInfo expectedUser = createUser();
        JwtRequest jwtRequest = new JwtRequest("email", "pass");
        when(userService.retrieveByEmail(expectedUser.getEmail())).thenReturn(expectedUser);
        when(jwtService.generateAccessToken(expectedUser)).thenReturn("access token");
        when(jwtService.generateRefreshToken(expectedUser)).thenReturn("refresh token");
        when(jwtService.getExpirationTime()).thenReturn(5L);
        JwtResponse jwtResponse = authenticationService.login(jwtRequest);
        assertThat(jwtResponse).isNotNull();
        verify(userService, times(1)).retrieveByEmail("email");
        verify(jwtService, times(1)).generateAccessToken(expectedUser);
        verify(jwtService, times(1)).generateRefreshToken(expectedUser);
        verify(jwtService, times(1)).getExpirationTime();
    }

    @Test
    public void verifyLoginThrowsAuthenticationExceptionTest() {
        UserInfo expectedUser = createUser();
        JwtRequest jwtRequest = new JwtRequest("email", "pass1");
        when(userService.retrieveByEmail(expectedUser.getEmail())).thenReturn(expectedUser);
        assertThrows(AuthenticationException.class, () -> authenticationService.login(jwtRequest));
        verify(userService, times(1)).retrieveByEmail("email");
        verify(jwtService, times(0)).generateAccessToken(expectedUser);
        verify(jwtService, times(0)).generateRefreshToken(expectedUser);
        verify(jwtService, times(0)).getExpirationTime();
    }

    @Test
    public void verifyRefreshTest() {
        UserInfo expectedUser = createUser();
        JwtRefreshRequest jwtRequest = new JwtRefreshRequest("token");
        when(jwtService.parseToken("token")).thenReturn(new DefaultClaims());
        when(userService.retrieveByEmail(null)).thenReturn(expectedUser);
        when(jwtService.generateAccessToken(expectedUser)).thenReturn("access token");
        when(jwtService.generateRefreshToken(expectedUser)).thenReturn("refresh token");
        when(jwtService.getExpirationTime()).thenReturn(5L);
        JwtResponse jwtResponse = authenticationService.refresh(jwtRequest);
        assertThat(jwtResponse).isNotNull();
        verify(userService, times(1)).retrieveByEmail(null);
        verify(jwtService, times(1)).generateAccessToken(expectedUser);
        verify(jwtService, times(1)).generateRefreshToken(expectedUser);
        verify(jwtService, times(1)).getExpirationTime();
    }

    private UserInfo createUser() {
        return new UserInfo(1L, "name", "surname", "email",
                "$2a$10$XKqS0Ov/axluIjvQhBmv7uiWF6wNY/FOabwyA/CIMDjqtLH2mrRkW", UserInfo.Role.ROLE_ADMIN);
    }

}
