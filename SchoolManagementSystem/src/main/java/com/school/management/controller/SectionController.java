package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SectionRequestDTO;
import com.school.management.service.SectionService;
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
@RequestMapping("/api/section")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createSection(@RequestBody final SectionRequestDTO sectionRequestDTO) {
        return this.sectionService.createSection(sectionRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveSectionById(@PathVariable final String id) {
        return this.sectionService.retrieveSectionById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllSections() {
        return this.sectionService.retrieveAllSections();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateSection(@PathVariable final String id, @RequestBody final SectionRequestDTO sectionRequestDTO) {
        return this.sectionService.updateSection(id, sectionRequestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeSectionById(@PathVariable final String id) {
        return this.sectionService.removeSectionById(id);
    }
}
