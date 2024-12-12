package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.TeacherRequestDTO;
import com.school.management.entity.School;
import com.school.management.entity.Teacher;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.SchoolRepository;
import com.school.management.repository.TeacherRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;
    private final SecurityUtil securityUtil;

    public TeacherService(TeacherRepository teacherRepository, SchoolRepository schoolRepository, SecurityUtil securityUtil) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.securityUtil = securityUtil;
    }

    @Transactional
    public ResponseDTO createTeacher(final TeacherRequestDTO teacherRequestDTO) {
        final School school = this.schoolRepository.findById(teacherRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Teacher teacher = Teacher.builder()
                .name(teacherRequestDTO.getName())
                .address(teacherRequestDTO.getAddress())
                .email(teacherRequestDTO.getEmail())
                .phoneNo(teacherRequestDTO.getPhoneNo())
                .gender(teacherRequestDTO.getGender())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .school(school)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.teacherRepository.save(teacher)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllTeachers() {
        final List<Teacher> teacher = this.teacherRepository.findAll();
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveTeacherById(final String id) {
        final Teacher teacher = this.teacherRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherRepository.findById(id)
        ).statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateTeacher(final String id, final TeacherRequestDTO teacherRequestDTO) {
        final Teacher existingTeacher = this.teacherRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (teacherRequestDTO.getName() != null) {
            existingTeacher.setName(teacherRequestDTO.getName());
        }
        if (teacherRequestDTO.getAddress() != null) {
            existingTeacher.setAddress(teacherRequestDTO.getAddress());
        }
        if (teacherRequestDTO.getEmail() != null) {
            existingTeacher.setEmail(teacherRequestDTO.getEmail());
        }
        if (teacherRequestDTO.getPhoneNo() != null) {
            existingTeacher.setPhoneNo(teacherRequestDTO.getPhoneNo());
        }
        if (teacherRequestDTO.getGender() != null) {
            existingTeacher.setGender(teacherRequestDTO.getGender());
        }
        if (teacherRequestDTO.getUpdatedBy() != null) {
            existingTeacher.setUpdatedBy(teacherRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.teacherRepository.save(existingTeacher)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeTeacherById(final String id) {
        if (!this.teacherRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}

