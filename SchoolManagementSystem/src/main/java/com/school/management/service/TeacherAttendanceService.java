package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.TeacherAttendanceRequestDTO;
import com.school.management.entity.Teacher;
import com.school.management.entity.TeacherAttendance;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.TeacherAttendanceRepository;
import com.school.management.repository.TeacherRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherAttendanceService {

    private final TeacherAttendanceRepository teacherAttendanceRepository;
    private final TeacherRepository teacherRepository;
    private final SecurityUtil securityUtil;

    public TeacherAttendanceService(TeacherAttendanceRepository teacherAttendanceRepository, TeacherRepository teacherRepository,SecurityUtil securityUtil) {
        this.teacherAttendanceRepository = teacherAttendanceRepository;
        this.teacherRepository = teacherRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createAttendance(final TeacherAttendanceRequestDTO teacherAttendanceRequestDTO) {
        final Teacher teacher = this.teacherRepository.findById(teacherAttendanceRequestDTO.getTeacherId()).orElseThrow(() -> new BadRequestServiceAlertException("Teacher not found"));
        final TeacherAttendance attendance = TeacherAttendance.builder()
                .date(teacherAttendanceRequestDTO.getDate())
                .status(teacherAttendanceRequestDTO.getStatus())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .teacher(teacher)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.teacherAttendanceRepository.save(attendance)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllAttendance() {
        final List<TeacherAttendance> teacherAttendance = this.teacherAttendanceRepository.findAll();
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherAttendanceRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAttendanceById(final String id) {
        final TeacherAttendance teacherAttendance = this.teacherAttendanceRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.teacherAttendanceRepository.findById(id))
                .statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateAttendance(final String id, final TeacherAttendanceRequestDTO requestDTO) {
        final TeacherAttendance existingAttendance = this.teacherAttendanceRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (requestDTO.getDate() != null) {
            existingAttendance.setDate(requestDTO.getDate());
        }
        if (requestDTO.getStatus() != null) {
            existingAttendance.setStatus(requestDTO.getStatus());
        }
        if (requestDTO.getUpdatedBy() != null) {
            existingAttendance.setUpdatedBy(requestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.teacherAttendanceRepository.save(existingAttendance)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeAttendanceById(final String id) {
        if (!this.teacherAttendanceRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
