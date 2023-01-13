package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateMarkGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
import com.solvd.laba.iis.web.mapper.MarkMapper;
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
        List<MarkDto> marks = markMapper.listToListDto(markService.getAll());
        return marks;
    }

    @GetMapping("/{id}")
    public MarkDto getById(@PathVariable long id) {
        MarkDto markDto = markMapper.markToMarkDto(markService.getById(id));
        return markDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkDto create(@RequestBody @Validated(OnCreateMarkGroup.class) MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.create(mark);
        return markMapper.markToMarkDto(mark);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        markService.delete(id);
    }

    @PutMapping
    public MarkDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.save(mark);
        return markMapper.markToMarkDto(mark);
    }

}
