package com.school.management.controller;

import com.school.management.dto.DynamicKeyValueDTO;
import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentMarkRequestDTO;
import com.school.management.service.StudentMarkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student-mark")
public class StudentMarkController {

    private final StudentMarkService studentMarkService;

    public StudentMarkController(StudentMarkService studentMarkService) {
        this.studentMarkService = studentMarkService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createStudentMark(@RequestBody final StudentMarkRequestDTO studentMarkRequestDTO) {
        return this.studentMarkService.createStudentMark(studentMarkRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveStudentMarkById(@PathVariable final String id) {
        return this.studentMarkService.retrieveStudentMarkById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllStudentMarks() {
        return this.studentMarkService.retrieveAllStudentMarks();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateStudentMark(@PathVariable final String id, @RequestBody final StudentMarkRequestDTO studentMarkRequestDTO) {
        return this.studentMarkService.updateStudentMark(id, studentMarkRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeStudentMarkById(@PathVariable final String id) {
        return this.studentMarkService.removeStudentMarkById(id);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO studentMarkList(
            @RequestParam(required = false) String section,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String teacher,
            @RequestParam(required = false) int range,
            @RequestParam(required = false) Boolean isGreaterThen) {
        return studentMarkService.studentMarkList(section, subject, teacher ,range,isGreaterThen);
    }

    @GetMapping("key-value")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<DynamicKeyValueDTO> getStudentMarks() {
        return studentMarkService.getStudentMarkDetails();
    }
}

