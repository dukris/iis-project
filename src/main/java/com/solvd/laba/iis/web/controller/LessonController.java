package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
@Tag(name = "Lesson Controller", description = "Methods for working with lessons")
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @GetMapping
    @Operation(summary = "Get all lessons")
    public List<LessonDto> getAll() {
        List<Lesson> lessons = lessonService.retrieveAll();
        return lessonMapper.entityToDto(lessons);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by id")
    public LessonDto getById(@PathVariable @Parameter(description = "Lesson's id") Long id) {
        Lesson lesson = lessonService.retrieveById(id);
        return lessonMapper.entityToDto(lesson);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new lesson")
    public LessonDto create(@RequestBody @Validated(OnCreateLessonGroup.class) @Parameter(description = "Information about lesson") LessonDto lessonDto) {
        Lesson lesson = lessonMapper.dtoToEntity(lessonDto);
        lesson = lessonService.create(lesson);
        lessonDto = lessonMapper.entityToDto(lesson);
        return lessonDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete lesson")
    public void delete(@PathVariable @Parameter(description = "Lesson's id") Long id) {
        lessonService.delete(id);
    }

    @PutMapping
    @Operation(summary = "Update information about lesson")
    public LessonDto update(@RequestBody @Validated(OnUpdateGroup.class) @Parameter(description = "Information about lesson") LessonDto lessonDto) {
        Lesson lesson = lessonMapper.dtoToEntity(lessonDto);
        lesson = lessonService.update(lesson);
        lessonDto = lessonMapper.entityToDto(lesson);
        return lessonDto;
    }

}
