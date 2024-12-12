package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.TeacherRequestDTO;
import com.school.management.service.TeacherService;
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
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createTeacher(@RequestBody final TeacherRequestDTO teacherRequestDTO) {
        return this.teacherService.createTeacher(teacherRequestDTO);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllTeachers() {
        return this.teacherService.retrieveAllTeachers();
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveTeacherById(@PathVariable final String id) {
        return this.teacherService.retrieveTeacherById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateTeacher(@PathVariable final String id, @RequestBody final TeacherRequestDTO teacherRequestDTO) {
        return this.teacherService.updateTeacher(id, teacherRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeTeacherById(@PathVariable final String id) {
        return this.teacherService.removeTeacherById(id);
    }
}
