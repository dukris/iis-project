package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.LessonMapper;
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
        List<Lesson> lessons = lessonService.retrieveAll();
        List<LessonDto> lessonDtos = lessonMapper.entityToDto(lessons);
        return lessonDtos;
    }

    @GetMapping("/{id}")
    public LessonDto getById(@PathVariable Long id) {
        Lesson lesson = lessonService.retrieveById(id);
        LessonDto lessonDto = lessonMapper.entityToDto(lesson);
        return lessonDto;
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
    public void delete(@PathVariable Long id) {
        lessonService.delete(id);
    }

    @PutMapping
    public LessonDto update(@RequestBody @Validated(OnUpdateGroup.class) LessonDto lessonDto) {
        Lesson lesson = lessonMapper.dtoToEntity(lessonDto);
        lesson = lessonService.update(lesson);
        lessonDto = lessonMapper.entityToDto(lesson);
        return lessonDto;
    }

}
