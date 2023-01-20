package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/marks")
public class MarkController {

    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping()
    public List<MarkDto> getAll() {
        List<Mark> marks = markService.retrieveAll();
        List<MarkDto> markDtos = markMapper.entityToDto(marks);
        return markDtos;
    }

    @GetMapping("/{id}")
    public MarkDto getById(@PathVariable Long id) {
        Mark mark = markService.retrieveById(id);
        MarkDto markDto = markMapper.entityToDto(mark);
        return markDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAccessToMark(#markDto.teacher.id, #markDto.student.id, #markDto.subject.id)")
    public MarkDto create(@RequestBody @Validated(OnCreateMarkGroup.class) MarkDto markDto) {
        Mark mark = markMapper.dtoToEntity(markDto);
        mark = markService.create(mark);
        markDto = markMapper.entityToDto(mark);
        return markDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        markService.delete(id);
    }

    @PutMapping
    @PreAuthorize("hasAccessToMark(#markDto.teacher.id, #markDto.student.id, #markDto.subject.id)")
    public MarkDto update(@RequestBody @Validated(OnUpdateGroup.class) MarkDto markDto) {
        Mark mark = markMapper.dtoToEntity(markDto);
        mark = markService.update(mark);
        markDto = markMapper.entityToDto(mark);
        return markDto;
    }

}
