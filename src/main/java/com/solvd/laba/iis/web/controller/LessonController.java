package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/teacher")
    public List<LessonDto> getByTeacherAndDay(@RequestParam(name = "teacherId") long teacherId,
                                              @RequestParam(name = "weekday", required = false) String weekday) {
        List<LessonDto> lessons = Objects.nonNull(weekday) ?
                lessonService.getByTeacherAndDay(teacherId, weekday).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList() :
                lessonService.getByTeacher(teacherId).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList();
        return lessons;
    }

    @GetMapping("/group")
    public List<LessonDto> getByGroupAndDay(@RequestParam(name = "groupId") long groupId,
                                            @RequestParam(name = "weekday", required = false) String weekday) {
        List<LessonDto> lessons = Objects.nonNull(weekday) ?
                lessonService.getByGroupAndDay(groupId, weekday).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList() :
                lessonService.getByGroup(groupId).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList();
        return lessons;
    }

    @PostMapping
    public LessonDto create(@RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.create(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }

    @DeleteMapping
    public void delete(@RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lessonService.delete(lesson);
    }

    @PutMapping
    public LessonDto update(@RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.save(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }
}
