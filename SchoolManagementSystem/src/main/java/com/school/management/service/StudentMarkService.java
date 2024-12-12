package com.school.management.service;

import com.school.management.dto.DynamicKeyValueDTO;
import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentMarkRequestDTO;
import com.school.management.entity.Exam;
import com.school.management.entity.Student;
import com.school.management.entity.StudentMark;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.ExamRepository;
import com.school.management.repository.StudentMarkRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentMarkService {

    private final StudentMarkRepository studentMarkRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final SecurityUtil securityUtil;

    public StudentMarkService(StudentMarkRepository studentMarkRepository, StudentRepository studentRepository, ExamRepository examRepository, SecurityUtil securityUtil) {
        this.studentMarkRepository = studentMarkRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
        this.securityUtil = securityUtil;
    }

    @Transactional
    public ResponseDTO createStudentMark(final StudentMarkRequestDTO studentMarkRequestDTO) {
        final Student student = this.studentRepository.findById(studentMarkRequestDTO.getStudentId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Exam exam = this.examRepository.findById(studentMarkRequestDTO.getExamId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final StudentMark studentMark = StudentMark.builder()
                .mark1(studentMarkRequestDTO.getMark1())
                .mark2(studentMarkRequestDTO.getMark2())
                .mark3(studentMarkRequestDTO.getMark3())
                .mark4(studentMarkRequestDTO.getMark4())
                .mark5(studentMarkRequestDTO.getMark5())
                .total(studentMarkRequestDTO.getTotal())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .student(student)
                .exam(exam)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.studentMarkRepository.save(studentMark)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllStudentMarks() {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentMarkRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveStudentMarkById(final String id) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentMarkRepository.findById(id))
                .statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updateStudentMark(final String id, final StudentMarkRequestDTO studentMarkRequestDTO) {
        final StudentMark existingStudentMark = this.studentMarkRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (studentMarkRequestDTO.getMark1() != 0) {
            existingStudentMark.setMark1(studentMarkRequestDTO.getMark1());
        }
        if (studentMarkRequestDTO.getMark2() != 0) {
            existingStudentMark.setMark2(studentMarkRequestDTO.getMark2());
        }
        if (studentMarkRequestDTO.getMark3() != 0) {
            existingStudentMark.setMark3(studentMarkRequestDTO.getMark3());
        }
        if (studentMarkRequestDTO.getMark4() != 0) {
            existingStudentMark.setMark4(studentMarkRequestDTO.getMark4());
        }
        if (studentMarkRequestDTO.getMark5() != 0) {
            existingStudentMark.setMark5(studentMarkRequestDTO.getMark5());
        }
        existingStudentMark.setTotal(studentMarkRequestDTO.getTotal());
        if (studentMarkRequestDTO.getUpdatedBy() != null) {
            existingStudentMark.setUpdatedBy(studentMarkRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.studentMarkRepository.save(existingStudentMark)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeStudentMarkById(final String id) {
        if (!this.studentMarkRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.DELETED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO studentMarkList(String section, String subject, String teacher,int range,Boolean isGreaterThen) {
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(studentMarkRepository.studentMarkList())
                .statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public List<DynamicKeyValueDTO> getStudentMarkDetails() {
        List<Object[]> results = studentMarkRepository.studentMarkList();
        List<DynamicKeyValueDTO> dynamicDTOList = new ArrayList<>();

        for (Object[] result : results) {
            DynamicKeyValueDTO dto = new DynamicKeyValueDTO();
            dto.put("sectionName", result[0]);
            dto.put("subjectId", result[1]);
            dto.put("subjectName", result[2]);
            dto.put("teacherName", result[3]);
            dto.put("teacherId", result[4]);
            dto.put("total", result[5]);
            dynamicDTOList.add(dto);
        }

        return dynamicDTOList;
    }
}
