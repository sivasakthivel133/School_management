package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentAttendanceRequestDTO;
import com.school.management.entity.Student;
import com.school.management.entity.StudentAttendance;
import com.school.management.repository.StudentAttendanceRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentAttendanceServiceTest {

    @InjectMocks
    private StudentAttendanceService studentAttendanceService;

    @Mock
    private StudentAttendanceRepository studentAttendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String STUDENT_ID = "student-1";
    private static final String ATTENDANCE_ID = "attendance-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAttendance() {
        StudentAttendanceRequestDTO requestDTO = new StudentAttendanceRequestDTO();
        requestDTO.setStudentId(STUDENT_ID);
        requestDTO.setStatus("Present");
        requestDTO.setReason("fever");
        requestDTO.setDate("2024-12-01");

        Student student = mock(Student.class);
        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(student));

        StudentAttendance attendance = mock(StudentAttendance.class);
        when(studentAttendanceRepository.save(any(StudentAttendance.class))).thenReturn(attendance);

        ResponseDTO responseDTO = studentAttendanceService.createAttendance(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(attendance, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(studentAttendanceRepository, times(1)).save(any(StudentAttendance.class));
    }

    @Test
    void testRetrieveAllAttendanceRecords() {
        StudentAttendance attendance = mock(StudentAttendance.class);

        List<StudentAttendance> attendanceList = Stream.ofNullable(attendance).toList();
        when(studentAttendanceRepository.findAll()).thenReturn(attendanceList);

        ResponseDTO responseDTO = studentAttendanceService.retrieveAllAttendanceRecords();

        assertNotNull(responseDTO.getData());
        assertEquals(attendanceList, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveAttendanceById() {
        StudentAttendance attendance = mock(StudentAttendance.class);
        when(studentAttendanceRepository.findById(ATTENDANCE_ID)).thenReturn(Optional.of(attendance));

        ResponseDTO responseDTO = studentAttendanceService.retrieveAttendanceById(ATTENDANCE_ID);

        Object data = responseDTO.getData();
        assertNotNull(responseDTO);
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateAttendance() {
        StudentAttendance existingAttendance = mock(StudentAttendance.class);
        StudentAttendanceRequestDTO requestDTO = new StudentAttendanceRequestDTO();
        requestDTO.setStatus("Absent");
        requestDTO.setReason("Sick");

        when(studentAttendanceRepository.findById(ATTENDANCE_ID)).thenReturn(Optional.of(existingAttendance));
        when(studentAttendanceRepository.save(any(StudentAttendance.class))).thenReturn(existingAttendance);

        ResponseDTO responseDTO = studentAttendanceService.updateAttendance(ATTENDANCE_ID, requestDTO);

        assertEquals(existingAttendance, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveAttendanceById() {
        when(studentAttendanceRepository.existsById(ATTENDANCE_ID)).thenReturn(true);

        ResponseDTO responseDTO = studentAttendanceService.removeAttendanceById(ATTENDANCE_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(ATTENDANCE_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        verify(studentAttendanceRepository, times(1)).existsById(ATTENDANCE_ID);
    }
}
