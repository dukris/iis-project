package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.dto.validation.OnCreateLessonGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateAndDeleteGroup;
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
        List<LessonDto> lessons = lessonMapper.listToListDto(lessonService.getAll());
        return lessons;
    }

    @GetMapping("/{id}")
    public LessonDto getById(@PathVariable long id) {
        LessonDto lesson = lessonMapper.lessonToLessonDto(lessonService.getById(id));
        return lesson;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto create(@RequestBody @Validated(OnCreateLessonGroup.class) LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.create(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lessonService.delete(lesson);
    }

    @PutMapping
    public LessonDto update(@RequestBody @Validated(OnUpdateAndDeleteGroup.class) LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.save(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }

}
