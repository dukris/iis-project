package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.service.MarkService;
import com.solvd.laba.iis.web.dto.MarkDto;
import com.solvd.laba.iis.web.mapper.MarkMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
public class MarkController {
    private final MarkService markService;
    private final MarkMapper markMapper;

    @GetMapping()
    public ResponseEntity<List<MarkDto>> getAll() {
        List<MarkDto> marks = markService.getAll().stream()
                .map(markMapper::markToMarkDto)
                .toList();
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkDto> getById(@PathVariable long id) {
        MarkDto markDto = markMapper.markToMarkDto(markService.getById(id));
        return new ResponseEntity<>(markDto, HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<MarkDto>> getBySubjectAndTeacher(@RequestParam(name = "subjectId") long subjectId,
                                                @RequestParam(name = "teacherId") long teacherId) {
        List<MarkDto> marks = markService.getBySubjectAndTeacher(subjectId, teacherId).stream()
                .map(markMapper::markToMarkDto)
                .toList();
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<List<MarkDto>> getByStudentAndSubject(@RequestParam(name = "studentId") long studentId,
                                                @RequestParam(name = "subjectId", required = false) Long subjectId) {
        List<MarkDto> marks = Objects.nonNull(subjectId) ?
                markService.getByStudentAndSubject(studentId, subjectId).stream()
                        .map(markMapper::markToMarkDto)
                        .toList() :
                markService.getByStudent(studentId).stream()
                        .map(markMapper::markToMarkDto)
                        .toList();
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MarkDto> create(@RequestBody @Valid MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.create(mark);
        return new ResponseEntity<>(markMapper.markToMarkDto(mark), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        markService.delete(mark);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<MarkDto> update(@RequestBody @Valid MarkDto markDto) {
        Mark mark = markMapper.markDtoToMark(markDto);
        mark = markService.save(mark);
        return new ResponseEntity<>(markMapper.markToMarkDto(mark), HttpStatus.OK);
    }
}
