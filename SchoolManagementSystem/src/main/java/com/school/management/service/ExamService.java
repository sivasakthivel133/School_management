package com.school.management.service;

import com.school.management.dto.ExamRequestDTO;
import com.school.management.dto.ResponseDTO;
import com.school.management.entity.Exam;
import com.school.management.entity.Standard;
import com.school.management.entity.Subject;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.ExamRepository;
import com.school.management.repository.StandardRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;
    private final SecurityUtil securityUtil;

    public ExamService(ExamRepository examRepository, StandardRepository standardRepository, SubjectRepository subjectRepository, SecurityUtil securityUtil) {
        this.examRepository = examRepository;
        this.standardRepository = standardRepository;
        this.subjectRepository = subjectRepository;
        this.securityUtil = securityUtil;
    }

    @Transactional
    public ResponseDTO createExam(final ExamRequestDTO examRequestDTO) {
        final Standard standard = this.standardRepository.findById(examRequestDTO.getClassId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Subject subject = this.subjectRepository.findById(examRequestDTO.getSubjectId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Exam exam = Exam.builder()
                .name(examRequestDTO.getName())
                .type(examRequestDTO.getType())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .standard(standard)
                .subject(subject)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.examRepository.save(exam)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllExams() {
        final List<Exam> exams = this.examRepository.findAll();
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.examRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveExamById(final String id) {
        final Exam exam = this.examRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.examRepository.findById(id))
                .statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateExam(final String id, final ExamRequestDTO examRequestDTO) {
        final Exam existingExam = this.examRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (examRequestDTO.getName() != null) {
            existingExam.setName(examRequestDTO.getName());
        }
        if (examRequestDTO.getType() != null) {
            existingExam.setType(examRequestDTO.getType());
        }
        if (examRequestDTO.getUpdatedBy() != null) {
            existingExam.setUpdatedBy(examRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.examRepository.save(existingExam)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeExamById(final String id) {
        if (!this.examRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}

