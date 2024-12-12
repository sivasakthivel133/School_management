package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.TeacherAttendanceRequestDTO;
import com.school.management.entity.Teacher;
import com.school.management.entity.TeacherAttendance;
import com.school.management.repository.TeacherAttendanceRepository;
import com.school.management.repository.TeacherRepository;
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

public class TeacherAttendanceServiceTest {

    @InjectMocks
    private TeacherAttendanceService teacherAttendanceService;

    @Mock
    private TeacherAttendanceRepository teacherAttendanceRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String TEACHER_ID = "teacher-1";
    private static final String ATTENDANCE_ID = "attendance-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAttendance() {
        TeacherAttendanceRequestDTO attendanceRequestDTO = new TeacherAttendanceRequestDTO();
        attendanceRequestDTO.setTeacherId(TEACHER_ID);
        attendanceRequestDTO.setStatus("Present");
        attendanceRequestDTO.setDate("2024-12-05");

        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.of(teacher));

        TeacherAttendance teacherAttendance = mock(TeacherAttendance.class);
        when(teacherAttendanceRepository.save(any(TeacherAttendance.class))).thenReturn(teacherAttendance);

        when(securityUtil.getCurrentUser()).thenReturn("testUser");

        ResponseDTO responseDTO = teacherAttendanceService.createAttendance(attendanceRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(teacherAttendance, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(teacherAttendanceRepository, times(1)).save(any(TeacherAttendance.class));
    }

    @Test
    void testRetrieveAllAttendance() {
        TeacherAttendance teacherAttendance = mock(TeacherAttendance.class);

        List<TeacherAttendance> attendanceList = Stream.ofNullable(teacherAttendance).toList();
        when(teacherAttendanceRepository.findAll()).thenReturn(attendanceList);

        ResponseDTO responseDTO = teacherAttendanceService.retrieveAllAttendance();

        assertNotNull(responseDTO.getData());
        assertEquals(attendanceList, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveAttendanceById() {
        TeacherAttendance teacherAttendance = mock(TeacherAttendance.class);

        when(teacherAttendanceRepository.findById(ATTENDANCE_ID)).thenReturn(Optional.of(teacherAttendance));
        ResponseDTO responseDTO = teacherAttendanceService.retrieveAttendanceById(ATTENDANCE_ID);

        Object data = responseDTO.getData();
        assertNotNull(responseDTO.getData());
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateAttendance() {
        TeacherAttendance existingAttendance = mock(TeacherAttendance.class);
        TeacherAttendanceRequestDTO attendanceRequestDTO = new TeacherAttendanceRequestDTO();
        attendanceRequestDTO.setStatus("Absent");

        when(teacherAttendanceRepository.findById(ATTENDANCE_ID)).thenReturn(Optional.of(existingAttendance));
        when(teacherAttendanceRepository.save(any(TeacherAttendance.class))).thenReturn(existingAttendance);

        ResponseDTO responseDTO = teacherAttendanceService.updateAttendance(ATTENDANCE_ID, attendanceRequestDTO);

        assertEquals(existingAttendance, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveAttendanceById() {
        when(teacherAttendanceRepository.existsById(ATTENDANCE_ID)).thenReturn(true);

        ResponseDTO responseDTO = teacherAttendanceService.removeAttendanceById(ATTENDANCE_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(ATTENDANCE_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
        verify(teacherAttendanceRepository, times(1)).existsById(ATTENDANCE_ID);
    }
}