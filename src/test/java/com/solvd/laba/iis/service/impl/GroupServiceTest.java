package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.GroupRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    public void verifyRetrieveAllTest() {
        List<Group> expectedGroups = createGroups();
        when(groupRepository.findAll()).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveAll();
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.findById(expectedGroup.getId())).thenReturn(Optional.of(expectedGroup));
        Group group = groupService.retrieveById(expectedGroup.getId());
        assertEquals(expectedGroup, group, "Objects are not equal");
        verify(groupRepository, times(1)).findById(expectedGroup.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> groupService.retrieveById(1L));
        verify(groupRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyRetrieveByTeacherAndSubjectTest() {
        List<Group> expectedGroups = createGroups();
        when(groupRepository.findByTeacherAndSubject(1L, 1L)).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveByTeacherAndSubject(1L, 1L);
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findByTeacherAndSubject(1L, 1L);
    }

    @Test
    public void verifyRetrieveByTeacherCriteriaTest() {
        List<Group> expectedGroups = createGroups();
        when(groupRepository.findByCriteria(1L, new GroupSearchCriteria())).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveByCriteria(1L, new GroupSearchCriteria());
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findByCriteria(1L, new GroupSearchCriteria());
    }

    @Test
    public void retrieveByTeacherAndSubjectCriteriaTest() {
        List<Group> expectedGroups = createGroups();
        when(groupRepository.findByCriteria(1L, new GroupSearchCriteria(1L))).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveByCriteria(1L, new GroupSearchCriteria(1L));
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findByCriteria(1L, new GroupSearchCriteria(1L));
    }

    @Test
    public void verifyCreateSuccessTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.isExist(expectedGroup.getNumber())).thenReturn(false);
        Group group = groupService.create(expectedGroup);
        assertThat(group).isNotNull();
        verify(groupRepository, times(1)).isExist(expectedGroup.getNumber());
        verify(groupRepository, times(1)).create(expectedGroup);
    }

    @Test
    public void verifyCreateThrowsResourceAlreadyExistExceptionTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.isExist(expectedGroup.getNumber())).thenReturn(true);
        assertThrows(ResourceAlreadyExistException.class, () -> groupService.create(expectedGroup));
        verify(groupRepository, times(1)).isExist(expectedGroup.getNumber());
        verify(groupRepository, times(0)).create(expectedGroup);
    }

    @Test
    public void verifyUpdateSuccessTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.findById(expectedGroup.getId())).thenReturn(Optional.of(expectedGroup));
        Group group = groupService.update(expectedGroup);
        assertThat(group).isNotNull();
        verify(groupRepository, times(1)).findById(expectedGroup.getId());
        verify(groupRepository, times(1)).update(expectedGroup);
    }

    @Test
    public void verifyUpdateThrowsResourceDoesNotExistExceptionTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.findById(expectedGroup.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> groupService.update(expectedGroup));
        verify(groupRepository, times(1)).findById(expectedGroup.getId());
        verify(groupRepository, times(0)).update(expectedGroup);
    }

    @Test
    public void verifyDeleteTest() {
        groupService.delete(1L);
        verify(groupRepository, times(1)).delete(1L);
    }

    private Group createGroup() {
        return new Group(1L, 951005);
    }

    private List<Group> createGroups() {
        return Lists.newArrayList(
                new Group(1L, 951005),
                new Group(2L, 952003),
                new Group(3L, 953004));
    }

}
