package com.solvd.laba.iis.web.controller;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.service.LessonService;
import com.solvd.laba.iis.web.dto.LessonDto;
import com.solvd.laba.iis.web.mapper.LessonMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<LessonDto>> getAll() {
        List<LessonDto> lessons = lessonService.getAll().stream()
                .map(lessonMapper::lessonToLessonDto)
                .toList();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getById(@PathVariable long id) {
        LessonDto lesson = lessonMapper.lessonToLessonDto(lessonService.getById(id));
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<LessonDto>> getByTeacherAndDay(@RequestParam(name = "teacherId") long teacherId,
                                              @RequestParam(name = "weekday", required = false) String weekday) {
        List<LessonDto> lessons = Objects.nonNull(weekday) ?
                lessonService.getByTeacherAndDay(teacherId, weekday).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList() :
                lessonService.getByTeacher(teacherId).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/group")
    public ResponseEntity<List<LessonDto>> getByGroupAndDay(@RequestParam(name = "groupId") long groupId,
                                            @RequestParam(name = "weekday", required = false) String weekday) {
        List<LessonDto> lessons = Objects.nonNull(weekday) ?
                lessonService.getByGroupAndDay(groupId, weekday).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList() :
                lessonService.getByGroup(groupId).stream()
                        .map(lessonMapper::lessonToLessonDto)
                        .toList();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LessonDto> create(@RequestBody @Valid LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.create(lesson);
        return new ResponseEntity<>(lessonMapper.lessonToLessonDto(lesson), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lessonService.delete(lesson);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<LessonDto> update(@RequestBody @Valid LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        lesson = lessonService.save(lesson);
        return new ResponseEntity<>(lessonMapper.lessonToLessonDto(lesson), HttpStatus.OK);
    }
}
