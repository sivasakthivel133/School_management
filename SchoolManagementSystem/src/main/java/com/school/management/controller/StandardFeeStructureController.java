package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StandardFeeStructureRequestDTO;
import com.school.management.service.StandardFeeStructureService;
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
@RequestMapping("/api/standard-fee-structure")
public class StandardFeeStructureController {

    private final StandardFeeStructureService standardFeeStructureService;

    public StandardFeeStructureController(StandardFeeStructureService standardFeeStructureService) {
        this.standardFeeStructureService = standardFeeStructureService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO createClassFeeStructure(@RequestBody final StandardFeeStructureRequestDTO requestDTO) {
        return this.standardFeeStructureService.createClassFeeStructure(requestDTO);
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveClassFeeStructureById(@PathVariable final String id) {
        return this.standardFeeStructureService.retrieveClassFeeStructureById(id);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO retrieveAllClassFeeStructures() {
        return this.standardFeeStructureService.retrieveAllClassFeeStructures();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO updateClassFeeStructure(@PathVariable final String id, @RequestBody final StandardFeeStructureRequestDTO requestDTO) {
        return this.standardFeeStructureService.updateClassFeeStructure(id, requestDTO);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseDTO removeClassFeeStructureById(@PathVariable final String id) {
        return this.standardFeeStructureService.removeClassFeeStructureById(id);
    }
}
