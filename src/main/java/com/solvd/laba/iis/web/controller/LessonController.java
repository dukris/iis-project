package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @GetMapping
    public List<LessonDto> getAll() {
        List<LessonDto> lessons = lessonService.getAll().stream()
                .map(lessonMapper::lessonToLessonDto)
                .toList();
        return lessons;
    }

    @GetMapping("/{id}")
    public LessonDto getById(@PathVariable long id) {
        LessonDto lesson = lessonMapper.lessonToLessonDto(lessonService.getById(id));
        return lesson;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto create(@RequestBody @Valid LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.create(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lessonService.delete(lesson);
    }

    @PutMapping
    public LessonDto update(@RequestBody @Valid LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.save(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }
}
