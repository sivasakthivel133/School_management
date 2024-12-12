package com.school.management.service;

import com.school.management.dto.DynamicKeyValueDTO;
import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StudentMarkRequestDTO;
import com.school.management.entity.Exam;
import com.school.management.entity.Student;
import com.school.management.entity.StudentMark;
import com.school.management.repository.ExamRepository;
import com.school.management.repository.StudentMarkRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
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

public class StudentMarkServiceTest {

    @InjectMocks
    private StudentMarkService studentMarkService;

    @Mock
    private StudentMarkRepository studentMarkRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String STUDENT_ID = "student-1";
    private static final String EXAM_ID = "exam-1";
    private static final String STUDENT_MARK_ID = "mark-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudentMark() {
        StudentMarkRequestDTO requestDTO = new StudentMarkRequestDTO();
        requestDTO.setStudentId(STUDENT_ID);
        requestDTO.setExamId(EXAM_ID);
        requestDTO.setMark1(90);
        requestDTO.setMark2(85);
        requestDTO.setMark3(80);
        requestDTO.setMark4(95);
        requestDTO.setMark5(88);
        requestDTO.setTotal(438);

        Student student = mock(Student.class);
        Exam exam = mock(Exam.class);
        StudentMark studentMark = mock(StudentMark.class);

        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.of(exam));
        when(studentMarkRepository.save(any(StudentMark.class))).thenReturn(studentMark);

        ResponseDTO responseDTO = studentMarkService.createStudentMark(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(studentMark, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(studentMarkRepository, times(1)).save(any(StudentMark.class));
    }

    @Test
    void testRetrieveAllStudentMarks() {
        StudentMark studentMark = mock(StudentMark.class);
        List<StudentMark> studentMarks = Stream.ofNullable(studentMark).toList();

        when(studentMarkRepository.findAll()).thenReturn(studentMarks);

        ResponseDTO responseDTO = studentMarkService.retrieveAllStudentMarks();

        assertNotNull(responseDTO.getData());
        assertEquals(studentMarks, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveStudentMarkById() {
        StudentMark studentMark = mock(StudentMark.class);

        when(studentMarkRepository.findById(STUDENT_MARK_ID)).thenReturn(Optional.of(studentMark));

        ResponseDTO responseDTO = studentMarkService.retrieveStudentMarkById(STUDENT_MARK_ID);
        Object data = responseDTO.getData();
        assertNotNull(responseDTO);
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateStudentMark() {
        StudentMarkRequestDTO requestDTO = mock(StudentMarkRequestDTO.class);
        StudentMark studentMark = mock(StudentMark.class);

        when(studentMarkRepository.findById(STUDENT_MARK_ID)).thenReturn(Optional.of(studentMark));
        when(studentMarkRepository.save(any(StudentMark.class))).thenReturn(studentMark);

        ResponseDTO responseDTO = studentMarkService.updateStudentMark(STUDENT_MARK_ID, requestDTO);

        assertNotNull(responseDTO);
        assertEquals(studentMark, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveStudentMarkById() {
        when(studentMarkRepository.existsById(STUDENT_MARK_ID)).thenReturn(true);

        ResponseDTO responseDTO = studentMarkService.removeStudentMarkById(STUDENT_MARK_ID);

        assertNotNull(responseDTO);
        assertEquals(STUDENT_MARK_ID, responseDTO.getData());
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testStudentMarkList() {
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"A", "SUB-101", "Mathematics", "John Smith", "TCH-202", 450});
        when(studentMarkRepository.studentMarkList()).thenReturn(mockResults);

        ResponseDTO responseDTO = studentMarkService.studentMarkList("A", "Mathematics", "John Smith", 400, true);

        assertNotNull(responseDTO);
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        assertEquals(mockResults, responseDTO.getData());
    }

    @Test
    void testGetStudentMarkDetails() {
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"A", "SUB-101", "Mathematics", "John Smith", "TCH-202", 450});
        when(studentMarkRepository.studentMarkList()).thenReturn(mockResults);

        List<DynamicKeyValueDTO> result = studentMarkService.getStudentMarkDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
        DynamicKeyValueDTO dto = result.get(0);
        assertEquals("A", dto.get("sectionName"));
        assertEquals("SUB-101", dto.get("subjectId"));
        assertEquals("Mathematics", dto.get("subjectName"));
        assertEquals("John Smith", dto.get("teacherName"));
        assertEquals("TCH-202", dto.get("teacherId"));
        assertEquals(450, dto.get("total"));

        verify(studentMarkRepository, times(1)).studentMarkList();
    }
}
