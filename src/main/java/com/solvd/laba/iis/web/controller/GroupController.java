package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.service.GroupService;
import com.solvd.laba.iis.web.dto.GroupDto;
import com.solvd.laba.iis.web.mapper.GroupMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<GroupDto>> getAll() {
        List<GroupDto> groups = groupService.getAll().stream()
                .map(groupMapper::groupToGroupDto)
                .toList();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getById(@PathVariable long id) {
        GroupDto group = groupMapper.groupToGroupDto(groupService.getById(id));
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<GroupDto>> getByTeacherAndSubject(@RequestParam(name = "teacherId") long teacherId,
                                                                 @RequestParam(name = "subjectId", required = false) Long subjectId) {
        List<GroupDto> groups = Objects.nonNull(subjectId) ?
                groupService.getByTeacherAndSubject(teacherId, subjectId).stream()
                        .map(groupMapper::groupToGroupDto)
                        .toList() :
                groupService.getByTeacher(teacherId).stream()
                        .map(groupMapper::groupToGroupDto)
                        .toList();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody @Valid GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.create(group);
        return new ResponseEntity<>(groupMapper.groupToGroupDto(group), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        groupService.delete(group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GroupDto> update(@RequestBody @Valid GroupDto groupDto) {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        group = groupService.save(group);
        return new ResponseEntity<>(groupMapper.groupToGroupDto(group), HttpStatus.OK);
    }
}
