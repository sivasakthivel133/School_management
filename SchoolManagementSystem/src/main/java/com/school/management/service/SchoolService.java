
package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SchoolRequestDTO;
import com.school.management.entity.School;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.SchoolRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import com.school.management.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;


@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SecurityUtil securityUtil;

    public SchoolService(final SchoolRepository schoolRepository,SecurityUtil securityUtil) {
        this.schoolRepository = schoolRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createSchool(final SchoolRequestDTO schoolRequestDTO) {
        final School school = School.builder()
                .name(schoolRequestDTO.getName())
                .location(schoolRequestDTO.getLocation())
                .email(schoolRequestDTO.getEmail())
                .phoneNo(schoolRequestDTO.getPhoneNo())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .build();
        return ResponseDTO.builder().message((Constants.CREATED)).data(this.schoolRepository.save(school)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO validateSchoolDTO(final SchoolRequestDTO schoolRequestDTO) {
        if (!UtilService.validateEmail(schoolRequestDTO.getEmail())) {
            throw new InputMismatchException(Constants.REGEX_MISS_MATCH);
        }
        String emailFound = this.schoolRepository.findByEmail(schoolRequestDTO.getEmail()).toString();
        if (emailFound != null) {
            throw new BadRequestServiceAlertException(Constants.EMAIL_ALREADY_EXISTS);
        }
        if (!UtilService.validatePhone(schoolRequestDTO.getPhoneNo())) {
            throw new InputMismatchException(Constants.REGEX_MISS_MATCH_PHONE);
        }
        String phoneFound = this.schoolRepository.findByPhoneNo(schoolRequestDTO.getPhoneNo()).toString();
        if (phoneFound != null) {
            throw new BadRequestServiceAlertException(Constants.PHONE_ALREADY_EXISTS);
        }
        return new ResponseDTO(Constants.CREATED, Constants.SUCCESS, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveSchools() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.schoolRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveSchoolById(final String id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.schoolRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND))).build();
    }


    @Transactional
    public ResponseDTO updateSchool(final String id, final SchoolRequestDTO schoolRequestDTO) {
        final School existingSchool = this.schoolRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (schoolRequestDTO.getName() != null) {
            existingSchool.setName(schoolRequestDTO.getName());
        }
        if (schoolRequestDTO.getLocation() != null) {
            existingSchool.setLocation(schoolRequestDTO.getLocation());
        }
        if (schoolRequestDTO.getEmail() != null) {
            existingSchool.setEmail(schoolRequestDTO.getEmail());
        }
        if (schoolRequestDTO.getPhoneNo() != null) {
            existingSchool.setPhoneNo(schoolRequestDTO.getPhoneNo());
        }
        if (schoolRequestDTO.getUpdatedBy() != null) {
            existingSchool.setUpdatedBy(schoolRequestDTO.getUpdatedBy());
        }
        if (schoolRequestDTO.getCreatedBy() != null) {
            existingSchool.setCreatedBy(schoolRequestDTO.getCreatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.schoolRepository.save(existingSchool)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeSchoolById(final String id) {
        if (!this.schoolRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND + id);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    private SchoolRequestDTO convertToSchoolDTO(final School school) {
        final SchoolRequestDTO schoolDTO = new SchoolRequestDTO();
        schoolDTO.setName(school.getName());
        schoolDTO.setLocation(school.getLocation());
        schoolDTO.setPhoneNo(school.getPhoneNo());
        schoolDTO.setCreatedBy(school.getCreatedBy());
        schoolDTO.setUpdatedBy(school.getUpdatedBy());
        schoolDTO.setEmail(school.getEmail());
        return schoolDTO;
    }

    @Transactional
    public Page<SchoolRequestDTO> searchSchool(String search, Integer page, Integer size, String sortField, String sortDirection) {
        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 5, sort);
        final Page<School> school = this.schoolRepository.searchSchool(search, pageable);
        if (school.isEmpty()) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return school.map(this::convertToSchoolDTO);
    }
}