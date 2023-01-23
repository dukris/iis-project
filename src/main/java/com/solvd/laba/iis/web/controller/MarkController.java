package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/marks")
@Tag(name = "Mark Controller", description = "Methods for working with marks")
public class MarkController {

    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping()
    @Operation(summary = "Get all marks")
    public List<MarkDto> getAll() {
        List<Mark> marks = markService.retrieveAll();
        return markMapper.entityToDto(marks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mark by id")
    public MarkDto getById(@PathVariable @Parameter(description = "Mark's id") Long id) {
        Mark mark = markService.retrieveById(id);
        return markMapper.entityToDto(mark);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAccessToMark(#markDto.teacher.id, #markDto.student.id, #markDto.subject.id)")
    @Operation(summary = "Create new mark")
    public MarkDto create(@RequestBody @Validated(OnCreateMarkGroup.class) @Parameter(description = "Information about mark") MarkDto markDto) {
        Mark mark = markMapper.dtoToEntity(markDto);
        mark = markService.create(mark);
        markDto = markMapper.entityToDto(mark);
        return markDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAccessToDeleteMark(#id)")
    @Operation(summary = "Delete mark")
    public void delete(@PathVariable @Parameter(description = "Mark's id") Long id) {
        markService.delete(id);
    }

    @PutMapping
    @PreAuthorize("hasAccessToMark(#markDto.teacher.id, #markDto.student.id, #markDto.subject.id)")
    @Operation(summary = "Update information about mark")
    public MarkDto update(@RequestBody @Validated(OnUpdateGroup.class) @Parameter(description = "Information about mark") MarkDto markDto) {
        Mark mark = markMapper.dtoToEntity(markDto);
        mark = markService.update(mark);
        markDto = markMapper.entityToDto(mark);
        return markDto;
    }

}
