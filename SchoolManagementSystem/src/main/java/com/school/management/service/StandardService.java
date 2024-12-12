package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StandardRequestDTO;
import com.school.management.entity.School;
import com.school.management.entity.Standard;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.SchoolRepository;
import com.school.management.repository.StandardRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StandardService {

    private final StandardRepository standardRepository;
    private final SchoolRepository schoolRepository;
    private final SecurityUtil securityUtil;

    public StandardService(StandardRepository standardRepository, SchoolRepository schoolRepository,SecurityUtil securityUtil) {
        this.standardRepository = standardRepository;
        this.schoolRepository = schoolRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createClass(final StandardRequestDTO standardRequestDTO) {
        final School school = this.schoolRepository.findById(standardRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Standard standard = Standard.builder()
                .name(standardRequestDTO.getName())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .school(school)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.standardRepository.save(standard)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllClasses() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.standardRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveClassById(final String id) {
        final Standard standard = this.standardRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardRepository.findById(id))
                .statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateClass(final String id, final StandardRequestDTO standardRequestDTO) {
        final Standard existingStandard = this.standardRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (standardRequestDTO.getName() != null) {
            existingStandard.setName(standardRequestDTO.getName());
        }
        if (standardRequestDTO.getUpdatedBy() != null) {
            existingStandard.setUpdatedBy(standardRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.standardRepository.save(existingStandard)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeClassById(final String id) {
        if (!standardRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
