package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.LectureDto;
import com.example.coursefordevelopment.service.LectureService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LectureController {
    LectureService lectureService;
    @GetMapping("/getLectureBySection/{sectionId}")
    public ResponseEntity<List<LectureDto>> lectureBySection(@PathVariable("sectionId") Long sectionId) {
        return ResponseEntity.ok(lectureService.getLessonsBySectionId(sectionId));
    }
}
