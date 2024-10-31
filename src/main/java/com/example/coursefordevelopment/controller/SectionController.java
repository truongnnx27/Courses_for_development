package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.SectionDto;
import com.example.coursefordevelopment.service.SectionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SectionController {
    SectionService sectionService;
    @GetMapping("/getSectionInCourse/{idCourse}")
    public ResponseEntity<List<SectionDto>> getSectionInCourse(@PathVariable Long idCourse) {
        return ResponseEntity.ok(sectionService.getSectionsByCourseId(idCourse));
    }
}
