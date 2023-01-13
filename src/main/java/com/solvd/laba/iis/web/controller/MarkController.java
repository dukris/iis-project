package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.mark.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.mark.MarkDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.mark.MarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        List<MarkDto> marks = markMapper.entityToDto(markService.findAll());
        return marks;
    }

    @GetMapping("/{id}")
    public MarkDto getById(@PathVariable long id) {
        MarkDto markDto = markMapper.entityToDto(markService.findById(id));
        return markDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkDto create(@RequestBody @Validated(OnCreateMarkGroup.class) MarkDto markDto) {
        Mark mark = markMapper.dtoToEntity(markDto);
        mark = markService.create(mark);
        markDto = markMapper.entityToDto(mark);
        return markDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        markService.delete(id);
    }

    @PutMapping
    public MarkDto update(@RequestBody @Validated(OnUpdateGroup.class) MarkDto markDto) {
        Mark mark = markMapper.dtoToEntity(markDto);
        mark = markService.save(mark);
        markDto = markMapper.entityToDto(mark);
        return markDto;
    }

}
