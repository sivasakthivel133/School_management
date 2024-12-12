package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentRequestDTO;
import com.school.management.entity.School;
import com.school.management.entity.Standard;
import com.school.management.entity.Student;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.SchoolRepository;
import com.school.management.repository.StandardRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final StandardRepository standardRepository;
    private final SecurityUtil securityUtil;

    public StudentService(StudentRepository studentRepository, SchoolRepository schoolRepository, StandardRepository standardRepository, SecurityUtil securityUtil) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.standardRepository = standardRepository;
        this.securityUtil = securityUtil;
    }

    @Transactional
    public ResponseDTO createStudent(final StudentRequestDTO studentRequestDTO) {
        final School school = this.schoolRepository.findById(studentRequestDTO.getSchoolId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Standard standard = this.standardRepository.findById(studentRequestDTO.getStandardId()).orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        final Student student = Student.builder()
                .name(studentRequestDTO.getName())
                .address(studentRequestDTO.getAddress())
                .email(studentRequestDTO.getEmail())
                .gender(studentRequestDTO.getGender())
                .dob(studentRequestDTO.getDob())
                .phoneNo(studentRequestDTO.getPhoneNo())
                .joiningDate(studentRequestDTO.getJoiningDate())
                .createdBy(securityUtil.getCurrentUser())
                .updatedBy(securityUtil.getCurrentUser())
                .school(school)
                .standard(standard)
                .build();
        return ResponseDTO.builder().message(Constants.CREATED).data(this.studentRepository.save(student)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveAllStudents() {
        final List<Student> student = this.studentRepository.findAll();
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO retrieveStudentById(final String id) {
        final Student student = this.studentRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.RETRIEVED).data(this.studentRepository.findById(id)
           ).statusValue(HttpStatus.OK.name()).build();
    }

    @Transactional
    public ResponseDTO updatedStudent(final String id, final StudentRequestDTO studentRequestDTO) {
        final Student existingStudent = this.studentRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND));
        if (studentRequestDTO.getName() != null) {
            existingStudent.setName(studentRequestDTO.getName());
        }
        if (studentRequestDTO.getAddress() != null) {
            existingStudent.setAddress(studentRequestDTO.getAddress());
        }
        if (studentRequestDTO.getEmail() != null) {
            existingStudent.setEmail(studentRequestDTO.getEmail());
        }
        if (studentRequestDTO.getPhoneNo() != null) {
            existingStudent.setPhoneNo(studentRequestDTO.getPhoneNo());
        }
        if (studentRequestDTO.getDob() != null) {
            existingStudent.setDob(studentRequestDTO.getDob());
        }
        if (studentRequestDTO.getJoiningDate() != null) {
            existingStudent.setJoiningDate(studentRequestDTO.getJoiningDate());
        }
        if (studentRequestDTO.getGender() != null) {
            existingStudent.setGender(studentRequestDTO.getGender());
        }
        if (studentRequestDTO.getUpdatedBy() != null) {
            existingStudent.setUpdatedBy(studentRequestDTO.getUpdatedBy());
        }
        return ResponseDTO.builder().message(Constants.UPDATED).data(this.studentRepository.save(existingStudent)).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    @Transactional
    public ResponseDTO removeStudentById(final String id) {
        if (!this.studentRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }
        return ResponseDTO.builder().message(Constants.REMOVED).data(id).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    private StudentRequestDTO convertToStudentDTO(final Student student) {
        final StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName(student.getName());
        studentRequestDTO.setAddress(student.getAddress());
        studentRequestDTO.setPhoneNo(student.getPhoneNo());
        studentRequestDTO.setCreatedBy(student.getCreatedBy());
        studentRequestDTO.setUpdatedBy(student.getUpdatedBy());
        return studentRequestDTO;
    }

    @Transactional
    public Page<StudentRequestDTO> searchStudents(String search, Integer page, Integer size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 5, sort);
        final Page<Student> students = this.studentRepository.searchStudents(search, pageable);
        if (students.isEmpty()) {
            throw new BadRequestServiceAlertException("No data found for the given search criteria");
        }
        return students.map(this::convertToStudentDTO);
    }
}
