package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.TeacherRequestDTO;
import com.school.management.entity.School;
import com.school.management.entity.Teacher;
import com.school.management.repository.SchoolRepository;
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

public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String SCHOOL_ID = "school-1";
    private static final String TEACHER_ID = "teacher-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeacher() {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setSchoolId(SCHOOL_ID);
        teacherRequestDTO.setName("Jane Doe");
        teacherRequestDTO.setAddress("456 Main St");
        teacherRequestDTO.setEmail("janedoe@example.com");
        teacherRequestDTO.setPhoneNo("0987654321");
        teacherRequestDTO.setGender("Female");

        School school = mock(School.class);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(school));

        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        ResponseDTO responseDTO = teacherService.createTeacher(teacherRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(teacher, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    void testRetrieveAllTeachers() {
        Teacher teacher = mock(Teacher.class);

        List<Teacher> teacherList = Stream.ofNullable(teacher).toList();
        when(teacherRepository.findAll()).thenReturn(teacherList);

        ResponseDTO responseDTO = teacherService.retrieveAllTeachers();

        assertNotNull(responseDTO.getData());
        assertEquals(responseDTO.getData(), teacherList);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveTeacherById() {
        Teacher teacher = mock(Teacher.class);

        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.ofNullable(teacher));
        ResponseDTO responseDTO = teacherService.retrieveTeacherById(TEACHER_ID);

        assertNotNull(responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateTeacher() {
        Teacher teacher = mock(Teacher.class);
        TeacherRequestDTO teacherDTO = mock(TeacherRequestDTO.class);

        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.ofNullable(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        ResponseDTO responseDTO = teacherService.updateTeacher(TEACHER_ID, teacherDTO);

        assertEquals(responseDTO.getData(), teacher);
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveTeacher() {
        when(teacherRepository.existsById(TEACHER_ID)).thenReturn(true);

        ResponseDTO responseDTO = teacherService.removeTeacherById(TEACHER_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(TEACHER_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }
}
