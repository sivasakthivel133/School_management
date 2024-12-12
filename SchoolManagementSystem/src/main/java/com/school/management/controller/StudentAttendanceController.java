package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentAttendanceRequestDTO;
import com.school.management.service.StudentAttendanceService;
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
@RequestMapping("/api/student-attendance")
public class StudentAttendanceController {

    private final StudentAttendanceService studentAttendanceService;

    public StudentAttendanceController(StudentAttendanceService studentAttendanceService) {
        this.studentAttendanceService = studentAttendanceService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createAttendance(@RequestBody final StudentAttendanceRequestDTO studentAttendanceRequestDTO) {
        return this.studentAttendanceService.createAttendance(studentAttendanceRequestDTO);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllAttendanceRecords() {
        return this.studentAttendanceService.retrieveAllAttendanceRecords();
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAttendanceById(@PathVariable final String id) {
        return this.studentAttendanceService.retrieveAttendanceById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateAttendance(@PathVariable final String id, @RequestBody final StudentAttendanceRequestDTO studentAttendanceRequestDTO) {
        return this.studentAttendanceService.updateAttendance(id, studentAttendanceRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeAttendanceById(@PathVariable final String id) {
        return this.studentAttendanceService.removeAttendanceById(id);
    }
}

