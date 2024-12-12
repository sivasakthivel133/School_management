package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SchoolRequestDTO;
import com.school.management.service.SchoolService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController( SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createSchool(@RequestBody final SchoolRequestDTO schoolRequestDTO) {
        return this.schoolService.createSchool(schoolRequestDTO);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO retrieveSchools() {
        return this.schoolService.retrieveSchools();
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO retrieveSchoolById(@PathVariable("id") final String id) {
        return this.schoolService.retrieveSchoolById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateSchool(@PathVariable final String id, @RequestBody final SchoolRequestDTO schoolRequestDTO) {
        return this.schoolService.updateSchool(id, schoolRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeSchoolById(@PathVariable final String id) {
        return this.schoolService.removeSchoolById(id);
    }

    @PostMapping("/validate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO validateSchoolDTO(@RequestBody final SchoolRequestDTO schoolRequestDTO) {
        return this.schoolService.validateSchoolDTO(schoolRequestDTO);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO searchSchool(@RequestParam(required = false) String search,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size,
                                    @RequestParam(required = false, defaultValue = "name") String sortField,
                                    @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Page<SchoolRequestDTO> SchoolDTOS = this.schoolService.searchSchool(search, page, size, sortField, sortDirection);
        return new ResponseDTO(Constants.RETRIEVED, SchoolDTOS, HttpStatus.OK.getReasonPhrase());
    }
}
