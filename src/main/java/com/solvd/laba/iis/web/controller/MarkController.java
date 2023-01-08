package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
public class MarkController {
    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping("/teacher")
    public List<MarkDto> getBySubjectAndTeacher(@RequestParam(name = "subjectId") long subjectId,
                                                @RequestParam(name = "teacherId") long teacherId) {
        List<MarkDto> marks = markService.getBySubjectAndTeacher(subjectId, teacherId).stream()
                .map(markMapper::markToMarkDto)
                .toList();
        return marks;
    }

    @GetMapping("/student")
    public List<MarkDto> getByStudentAndSubject(@RequestParam(name = "studentId") long studentId,
                                                @RequestParam(name = "subjectId", required = false) Long subjectId) {
        List<MarkDto> marks = Objects.nonNull(subjectId) ?
                markService.getByStudentAndSubject(studentId, subjectId).stream()
                        .map(markMapper::markToMarkDto)
                        .toList() :
                markService.getByStudent(studentId).stream()
                        .map(markMapper::markToMarkDto)
                        .toList();
        return marks;
    }

    @PostMapping
    public MarkDto create(@RequestBody MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.create(mark);
        return markMapper.markToMarkDto(mark);
    }

    @DeleteMapping
    public void delete(@RequestBody MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        markService.delete(mark);
    }

    @PutMapping
    public MarkDto update(@RequestBody MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.save(mark);
        return markMapper.markToMarkDto(mark);
    }
}
