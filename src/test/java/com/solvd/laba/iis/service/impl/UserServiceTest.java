package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void verifyRetrieveAllTest() {
        List<UserInfo> expectedUsers = createUsers();
        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<UserInfo> users = userService.retrieveAll();
        assertEquals(expectedUsers, users, "Objects are not equal");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        UserInfo expectedUser = createUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        UserInfo user = userService.retrieveById(expectedUser.getId());
        assertEquals(expectedUser, user, "Objects are not equal");
        verify(userRepository, times(1)).findById(expectedUser.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> userService.retrieveById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void verifyRetrieveByEmailSuccessTest() {
        UserInfo expectedUser = createUser();
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));
        UserInfo user = userService.retrieveByEmail(expectedUser.getEmail());
        assertEquals(expectedUser, user, "Objects are not equal");
        verify(userRepository, times(1)).findByEmail(expectedUser.getEmail());
    }

    @Test
    public void verifyRetrieveByEmailThrowsResourceDoesNotExistExceptionTest() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> userService.retrieveByEmail("email"));
        verify(userRepository, times(1)).findByEmail("email");
    }

    @Test
    public void verifyCreateSuccessTest() {
        UserInfo expectedUser = createUser();
        UserInfo user = createUser();
        user.setId(null);
        when(userRepository.isExist(expectedUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(expectedUser.getPassword())).thenReturn("pass");
        doAnswer(invocation -> {
            UserInfo receivedUser = invocation.getArgument(0);
            receivedUser.setId(1L);
            return null;
        }).when(userRepository).create(user);
        user = userService.create(user);
        assertEquals(expectedUser, user, "Objects are not equal");
        verify(userRepository, times(1)).isExist(expectedUser.getEmail());
        verify(userRepository, times(1)).create(expectedUser);
    }

    @Test
    public void verifyCreateThrowsResourceAlreadyExistExceptionTest() {
        UserInfo expectedUser = createUser();
        when(userRepository.isExist(expectedUser.getEmail())).thenReturn(true);
        assertThrows(ResourceAlreadyExistException.class, () -> userService.create(expectedUser));
        verify(userRepository, times(1)).isExist(expectedUser.getEmail());
        verify(userRepository, times(0)).create(expectedUser);
    }

    @Test
    public void verifyUpdateSuccessTest() {
        UserInfo expectedUser = createUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        when(passwordEncoder.encode(expectedUser.getPassword())).thenReturn("pass");
        UserInfo user = userService.update(expectedUser);
        assertThat(user).isNotNull();
        verify(userRepository, times(1)).findById(expectedUser.getId());
        verify(userRepository, times(1)).update(expectedUser);
    }

    @Test
    public void verifyUpdateThrowsResourceDoesNotExistExceptionTest() {
        UserInfo expectedUser = createUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> userService.update(expectedUser));
        verify(userRepository, times(1)).findById(expectedUser.getId());
        verify(userRepository, times(0)).update(expectedUser);
    }

    @Test
    public void verifyDeleteTest() {
        Long userId = 1L;
        userService.delete(userId);
        verify(userRepository, times(1)).delete(1L);
    }

    private UserInfo createUser() {
        return new UserInfo(1L, "name", "surname", "email", "pass", UserInfo.Role.ROLE_ADMIN);
    }

    private List<UserInfo> createUsers() {
        return Lists.list(
                new UserInfo(1L, "name", "surname", "email", "pass", UserInfo.Role.ROLE_ADMIN),
                new UserInfo(2L, "name", "surname", "email", "pass", UserInfo.Role.ROLE_TEACHER),
                new UserInfo(3L, "name", "surname", "email", "pass", UserInfo.Role.ROLE_STUDENT));
    }

}
