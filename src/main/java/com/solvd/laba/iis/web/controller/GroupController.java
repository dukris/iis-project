package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.web.dto.GroupDto;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping
    public List<GroupDto> getAll() {
        List<GroupDto> groups = groupService.getAll().stream()
                .map(groupMapper::groupToGroupDto)
                .toList();
        return groups;
    }

    @GetMapping("/{id}")
    public GroupDto getById(@PathVariable long id) {
        GroupDto group = groupMapper.groupToGroupDto(groupService.getById(id));
        return group;
    }

    @GetMapping("/teacher")
    public List<GroupDto> getByTeacherAndSubject(@RequestParam(name = "teacherId") long teacherId,
                                                 @RequestParam(name = "subjectId", required = false) Long subjectId) {
        List<GroupDto> groups = Objects.nonNull(subjectId) ?
                groupService.getByTeacherAndSubject(teacherId, subjectId).stream()
                        .map(groupMapper::groupToGroupDto)
                        .toList() :
                groupService.getByTeacher(teacherId).stream()
                        .map(groupMapper::groupToGroupDto)
                        .toList();
        return groups;
    }

    @PostMapping
    public GroupDto create(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.create(group);
        return groupMapper.groupToGroupDto(group);
    }

    @DeleteMapping
    public void delete(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        groupService.delete(group);
    }

    @PutMapping
    public GroupDto update(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.save(group);
        return groupMapper.groupToGroupDto(group);
    }
}
