package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentRequestDTO;
import com.school.management.entity.School;
import com.school.management.entity.Standard;
import com.school.management.entity.Student;
import com.school.management.repository.SchoolRepository;
import com.school.management.repository.StandardRepository;
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

public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String SCHOOL_ID = "school-1";
    private static final String STANDARD_ID = "standard-1";
    private static final String STUDENT_ID = "student-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setSchoolId(SCHOOL_ID);
        studentRequestDTO.setStandardId(STANDARD_ID);
        studentRequestDTO.setName("John Doe");
        studentRequestDTO.setAddress("123 Main St");
        studentRequestDTO.setEmail("johndoe@example.com");
        studentRequestDTO.setPhoneNo("1234567890");
        studentRequestDTO.setDob("2000-01-01");
        studentRequestDTO.setGender("Male");
        studentRequestDTO.setJoiningDate("2024-01-01");


        School school = mock(School.class);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(school));

        Standard standard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));

        Student student = mock(Student.class);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseDTO responseDTO = studentService.createStudent(studentRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(student, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testRetrieve() {
        Student student = mock(Student.class);

        List<Student> studentList = Stream.ofNullable(student).toList();
        when(studentRepository.findAll()).thenReturn(studentList);

        ResponseDTO responseDTO = studentService.retrieveAllStudents();

        assertNotNull(responseDTO.getData());
        assertEquals(responseDTO.getData(), studentList);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveById() {
        Student student = mock(Student.class);

        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.ofNullable(student));
        ResponseDTO responseDTO = studentService.retrieveStudentById(STUDENT_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
        // verify(studentRepository, times(1)).findById(STUDENT_ID);
    }

    @Test
    void testUpdate() {
        Student student = mock(Student.class);
        StudentRequestDTO studentDTO = mock(StudentRequestDTO.class);

        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.ofNullable(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseDTO responseDTO = studentService.updatedStudent(STUDENT_ID, studentDTO);

        assertEquals(responseDTO.getData(), student);
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    public void testRemove() {

        when(studentRepository.existsById(STUDENT_ID)).thenReturn(true);

        ResponseDTO responseDTO = studentService.removeStudentById(STUDENT_ID);
        assertEquals(Constants.REMOVED, responseDTO.getMessage());
        assertEquals(STUDENT_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }
}


