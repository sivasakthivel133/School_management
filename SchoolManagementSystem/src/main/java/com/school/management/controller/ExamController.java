package com.school.management.controller;

import com.school.management.dto.ExamRequestDTO;
import com.school.management.dto.ResponseDTO;
import com.school.management.service.ExamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createExam(@RequestBody final ExamRequestDTO examRequestDTO) {
        return this.examService.createExam(examRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveExamById(@PathVariable final String id) {
        return this.examService.retrieveExamById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllExams() {
        return this.examService.retrieveAllExams();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateExam(@PathVariable final String id, @RequestBody final ExamRequestDTO examRequestDTO) {
        return this.examService.updateExam(id, examRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeExamById(@PathVariable final String id) {
        return this.examService.removeExamById(id);
    }
}

