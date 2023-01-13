package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.lesson.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.lesson.LessonDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.lesson.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @GetMapping
    public List<LessonDto> getAll() {
        List<LessonDto> lessons = lessonMapper.entityToDto(lessonService.findAll());
        return lessons;
    }

    @GetMapping("/{id}")
    public LessonDto getById(@PathVariable long id) {
        LessonDto lesson = lessonMapper.entityToDto(lessonService.findById(id));
        return lesson;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto create(@RequestBody @Validated(OnCreateLessonGroup.class) LessonDto lessonDto) {
        Lesson lesson = lessonMapper.dtoToEntity(lessonDto);
        lesson = lessonService.create(lesson);
        lessonDto = lessonMapper.entityToDto(lesson);
        return lessonDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        lessonService.delete(id);
    }

    @PutMapping
    public LessonDto update(@RequestBody @Validated(OnUpdateGroup.class) LessonDto lessonDto) {
        Lesson lesson = lessonMapper.dtoToEntity(lessonDto);
        lesson = lessonService.save(lesson);
        lessonDto = lessonMapper.entityToDto(lesson);
        return lessonDto;
    }

}
