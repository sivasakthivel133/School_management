package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentRequestDTO;
import com.school.management.service.StudentService;
import com.school.management.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createStudent(@RequestBody final StudentRequestDTO studentRequestDTO) {
        return this.studentService.createStudent(studentRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveStudentById(@PathVariable final String id) {
        return this.studentService.retrieveStudentById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllStudents() {
        return this.studentService.retrieveAllStudents();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateStudent(@PathVariable final String id, @RequestBody final StudentRequestDTO studentRequestDTO) {
        return this.studentService.updatedStudent(id, studentRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeStudentById(@PathVariable final String id) {
        return this.studentService.removeStudentById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO searchStudents(@RequestParam(required = false) String search,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size,
                                      @RequestParam(required = false, defaultValue = "name") String sortField,
                                      @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Page<StudentRequestDTO> SchoolDTOS = this.studentService.searchStudents(search, page, size, sortField, sortDirection);
        return new ResponseDTO(Constants.RETRIEVED, SchoolDTOS, HttpStatus.OK.getReasonPhrase());
    }
}
