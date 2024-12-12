
package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StandardRequestDTO;
import com.school.management.service.StandardService;
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
@RequestMapping("/api/standard")
public class StandardController {

    private final StandardService standardService;

    public StandardController(StandardService standardService) {
        this.standardService = standardService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO createClass(@RequestBody final StandardRequestDTO standardRequestDTO) {
        return this.standardService.createClass(standardRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveClassById(@PathVariable final String id) {
        return this.standardService.retrieveClassById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllClasses() {
        return this.standardService.retrieveAllClasses();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDTO updateClass(@PathVariable final String id, @RequestBody final StandardRequestDTO standardRequestDTO) {
        return this.standardService.updateClass(id, standardRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeClassById(@PathVariable final String id) {
        this.standardService.removeClassById(id);
        return this.standardService.removeClassById(id);
    }
}
