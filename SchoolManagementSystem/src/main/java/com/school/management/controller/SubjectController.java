package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SubjectRequestDTO;
import com.school.management.service.SubjectService;
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
@RequestMapping("/api/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createSubject(@RequestBody final SubjectRequestDTO subjectRequestDTO) {
        return this.subjectService.createSubject(subjectRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveSubjectById(@PathVariable final String id) {
        return this.subjectService.retrieveSubjectById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllSubjects() {
        return this.subjectService.retrieveAllSubjects();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateSubject(@PathVariable final String id, @RequestBody final SubjectRequestDTO subjectRequestDTO) {
        return this.subjectService.updateSubject(id, subjectRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeSubjectById(@PathVariable final String id) {
        return this.subjectService.removeSubjectById(id);
    }
}
