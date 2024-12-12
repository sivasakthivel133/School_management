package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SubjectRequestDTO;
import com.school.management.entity.Standard;
import com.school.management.entity.Subject;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.StandardRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StandardRepository standardRepository;
    private final SecurityUtil securityUtil;

    public SubjectService(SubjectRepository subjectRepository, StandardRepository standardRepository,SecurityUtil securityUtil) {
        this.subjectRepository = subjectRepository;
        this.standardRepository = standardRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createSubject(final SubjectRequestDTO subjectRequestDTO) {
        final Standard standard = this.standardRepository.findById(subjectRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException("Class with ID " + subjectRequestDTO.getStandardId() + " not found"));
        final Subject subject = Subject.builder()
                .name(subjectRequestDTO.getName())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .standard(standard)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.subjectRepository.save(subject)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllSubjects() {
        final List<Subject> subjects = this.subjectRepository.findAll();
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.subjectRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveSubjectById(final String id) {
        final Subject subject = this.subjectRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.subjectRepository.findById(id))
                .statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateSubject(final String id, final SubjectRequestDTO subjectRequestDTO) {
        final Subject existingSubject = this.subjectRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (subjectRequestDTO.getName() != null) {
            existingSubject.setName(subjectRequestDTO.getName());
        }
        if (subjectRequestDTO.getUpdatedBy() != null) {
            existingSubject.setUpdatedBy(subjectRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.subjectRepository.save(existingSubject)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeSubjectById(final String id) {
        if (!this.subjectRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
