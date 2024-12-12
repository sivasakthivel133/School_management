package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentAttendanceRequestDTO;
import com.school.management.entity.Student;
import com.school.management.entity.StudentAttendance;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.StudentAttendanceRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAttendanceService {

    private final StudentAttendanceRepository studentAttendanceRepository;
    private final StudentRepository studentRepository;
    private final SecurityUtil securityUtil;

    public StudentAttendanceService(StudentAttendanceRepository studentAttendanceRepository, StudentRepository studentRepository,SecurityUtil securityUtil) {
        this.studentAttendanceRepository = studentAttendanceRepository;
        this.studentRepository = studentRepository;
        this.securityUtil=securityUtil;
    }

    @Transactional
    public ResponseDTO createAttendance(final StudentAttendanceRequestDTO studentAttendanceRequestDTO) {
        final Student student = this.studentRepository.findById(studentAttendanceRequestDTO.getStudentId())
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final StudentAttendance studentAttendance = StudentAttendance.builder()
                .status(studentAttendanceRequestDTO.getStatus())
                .reason(studentAttendanceRequestDTO.getReason())
                .date(studentAttendanceRequestDTO.getDate())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .student(student)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.studentAttendanceRepository.save(studentAttendance)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllAttendanceRecords() {
        final List<StudentAttendance> studentAttendances = this.studentAttendanceRepository.findAll();
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentAttendanceRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAttendanceById(final String id) {
        final StudentAttendance studentAttendance = this.studentAttendanceRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentAttendanceRepository.findById(id)).statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateAttendance(final String id, final StudentAttendanceRequestDTO attendanceRequestDTO) {
        final StudentAttendance existingAttendance = this.studentAttendanceRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (attendanceRequestDTO.getStatus() != null) {
            existingAttendance.setStatus(attendanceRequestDTO.getStatus());
        }
        if (attendanceRequestDTO.getReason() != null) {
            existingAttendance.setReason(attendanceRequestDTO.getReason());
        }
        if (attendanceRequestDTO.getUpdatedBy() != null) {
            existingAttendance.setUpdatedBy(attendanceRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.studentAttendanceRepository.save(existingAttendance)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeAttendanceById(final String id) {
        if (!this.studentAttendanceRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }
}
