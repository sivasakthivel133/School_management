package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.TeacherAttendanceRequestDTO;
import com.school.management.service.TeacherAttendanceService;
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
@RequestMapping("/api/teacher-attendance")
public class TeacherAttendanceController {

    private final TeacherAttendanceService teacherAttendanceService;

    public TeacherAttendanceController(TeacherAttendanceService teacherAttendanceService) {
        this.teacherAttendanceService = teacherAttendanceService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createAttendance(@RequestBody final TeacherAttendanceRequestDTO teacherAttendanceRequestDTO) {
        return this.teacherAttendanceService.createAttendance(teacherAttendanceRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAttendanceById(@PathVariable final String id) {
        return this.teacherAttendanceService.retrieveAttendanceById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllAttendance() {
        return this.teacherAttendanceService.retrieveAllAttendance();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateAttendance(@PathVariable final String id, @RequestBody final TeacherAttendanceRequestDTO teacherAttendanceRequestDTO) {
        return this.teacherAttendanceService.updateAttendance(id, teacherAttendanceRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeAttendanceById(@PathVariable final String id) {
        return this.teacherAttendanceService.removeAttendanceById(id);
    }
}
