package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SectionRequestDTO;
import com.school.management.entity.Section;
import com.school.management.entity.Standard;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.SectionRepository;
import com.school.management.repository.StandardRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StandardRepository standardRepository;
    private final SecurityUtil securityUtil;

    public SectionService(SectionRepository sectionRepository, StandardRepository standardRepository,SecurityUtil securityUtil) {
        this.sectionRepository = sectionRepository;
        this.standardRepository = standardRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createSection(final SectionRequestDTO sectionRequestDTO) {
        final Standard standard = this.standardRepository.findById(sectionRequestDTO.getClassId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Section section = Section.builder()
                .name(sectionRequestDTO.getName())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .standard(standard)
                .build();
        return  ResponseDTO.builder().message(Constants.CREATED).data(this.sectionRepository.save(section)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllSections() {
        return  ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveSectionById(final String id) {
        return  ResponseDTO.builder().message(Constants.SUCCESS).data(this.sectionRepository.findById(id))
                .statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateSection(final String id, final SectionRequestDTO sectionRequestDTO) {
        Section existingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (sectionRequestDTO.getName() != null) {
            existingSection.setName(sectionRequestDTO.getName());
        }
        if (sectionRequestDTO.getUpdatedBy() != null) {
            existingSection.setUpdatedBy(sectionRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.sectionRepository.save(existingSection)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeSectionById(final String id) {
        if (!sectionRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return  ResponseDTO.builder().message(Constants.DELETED).data( id).statusValue( HttpStatus.OK.getReasonPhrase()).build();
    }
}








