package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StandardFeeStructureRequestDTO;
import com.school.management.entity.Standard;
import com.school.management.entity.StandardFeeStructure;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.StandardFeeStructureRepository;
import com.school.management.repository.StandardRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardFeeStructureService {

    private final StandardFeeStructureRepository standardFeeStructureRepository;
    private final StandardRepository standardRepository;
    private final SecurityUtil securityUtil;

    public StandardFeeStructureService(StandardFeeStructureRepository standardFeeStructureRepository, StandardRepository standardRepository,SecurityUtil securityUtil) {
        this.standardFeeStructureRepository = standardFeeStructureRepository;
        this.standardRepository = standardRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createClassFeeStructure(final StandardFeeStructureRequestDTO standardFeeStructureRequestDTO) {
        final Standard standard = this.standardRepository.findById(standardFeeStructureRequestDTO.getClassId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final StandardFeeStructure standardFeeStructure = StandardFeeStructure.builder()
                .feeAmount(standardFeeStructureRequestDTO.getFeeAmount())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .standard(standard)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.standardFeeStructureRepository.save(standardFeeStructure)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveClassFeeStructureById(final String id) {
        final StandardFeeStructure feeStructure = this.standardFeeStructureRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardFeeStructureRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND))).build();
    }

    public ResponseDTO retrieveAllClassFeeStructures() {
        List<StandardFeeStructure> feeStructures = this.standardFeeStructureRepository.findAll();
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.standardFeeStructureRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO updateClassFeeStructure(final String id, final StandardFeeStructureRequestDTO requestDTO) {
        final StandardFeeStructure existingFeeStructure = (StandardFeeStructure) retrieveClassFeeStructureById(id).getData();
        if (requestDTO.getFeeAmount() != 0) {
            existingFeeStructure.setFeeAmount(requestDTO.getFeeAmount());
        }
        if (requestDTO.getUpdatedBy() != null) {
            existingFeeStructure.setUpdatedBy(requestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.standardFeeStructureRepository.save(existingFeeStructure)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeClassFeeStructureById(final String id) {
        if (!standardFeeStructureRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
