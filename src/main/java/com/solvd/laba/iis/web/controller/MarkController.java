package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
public class MarkController {
    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping()
    public List<MarkDto> getAll() {
        List<MarkDto> marks = markService.getAll().stream()
                .map(markMapper::markToMarkDto)
                .toList();
        return marks;
    }

    @GetMapping("/{id}")
    public MarkDto getById(@PathVariable long id) {
        MarkDto markDto = markMapper.markToMarkDto(markService.getById(id));
        return markDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkDto create(@RequestBody @Valid MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.create(mark);
        return markMapper.markToMarkDto(mark);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        markService.delete(mark);
    }

    @PutMapping
    public MarkDto update(@RequestBody @Valid MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.save(mark);
        return markMapper.markToMarkDto(mark);
    }
}
