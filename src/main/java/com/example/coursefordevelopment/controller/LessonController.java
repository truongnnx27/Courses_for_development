package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.LessonDto;
import com.example.coursefordevelopment.dto.Request.ApiRespone;
import com.example.coursefordevelopment.entity.Lesson;
import com.example.coursefordevelopment.service.LessonService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class LessonController {
    LessonService lessonService;

    @PostMapping("/addNewLesson")
    public ResponseEntity<ApiRespone<LessonDto>> createLesson(@Valid @RequestBody LessonDto lessonDto)
    {
        LessonDto lesson = lessonService.createLesson(lessonDto);
        ApiRespone<LessonDto> respone = new ApiRespone<>(1000,"Lesson create successfully",lesson);
        return ResponseEntity.ok(respone);
    }

    @PutMapping("/updateLesson/{id}")
    public ResponseEntity<LessonDto> updateLesson(@RequestBody LessonDto lessonDto, @PathVariable Long id)
    {
        LessonDto updateLesson= lessonService.updateLesson(lessonDto,id);
        return ResponseEntity.ok(updateLesson);
    }
}
