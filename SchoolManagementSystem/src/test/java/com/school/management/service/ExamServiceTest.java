package com.school.management.service;

import com.school.management.dto.ExamRequestDTO;
import com.school.management.dto.ResponseDTO;
import com.school.management.entity.Exam;
import com.school.management.entity.Standard;
import com.school.management.entity.Subject;
import com.school.management.repository.ExamRepository;
import com.school.management.repository.StandardRepository;
import com.school.management.repository.SubjectRepository;
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

public class ExamServiceTest {

    @InjectMocks
    private ExamService examService;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String EXAM_ID = "exam-1";
    private static final String STANDARD_ID = "standard-1";
    private static final String SUBJECT_ID = "subject-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExam() {
        ExamRequestDTO examRequestDTO = new ExamRequestDTO();
        examRequestDTO.setClassId(STANDARD_ID);
        examRequestDTO.setSubjectId(SUBJECT_ID);
        examRequestDTO.setName("Mid Term Exam");
        examRequestDTO.setType("Written");

        Standard standard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));

        Subject subject = mock(Subject.class);
        when(subjectRepository.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));

        Exam exam = mock(Exam.class);
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ResponseDTO responseDTO = examService.createExam(examRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(exam, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test
    void testRetrieveAllExams() {
        Exam exam = mock(Exam.class);

        List<Exam> examList = Stream.of(exam).toList();
        when(examRepository.findAll()).thenReturn(examList);

        ResponseDTO responseDTO = examService.retrieveAllExams();

        assertNotNull(responseDTO.getData());
        assertEquals(responseDTO.getData(), examList);
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveExamById() {
        Exam exam = mock(Exam.class);

        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.of(exam));
        ResponseDTO responseDTO = examService.retrieveExamById(EXAM_ID);
        Object data = responseDTO.getData();
        assertNotNull(responseDTO.getData());
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateExam() {
        Exam existingExam = mock(Exam.class);
        ExamRequestDTO examRequestDTO = new ExamRequestDTO();
        examRequestDTO.setName("Final Term Exam");
        examRequestDTO.setType("Oral");

        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.of(existingExam));
        when(examRepository.save(any(Exam.class))).thenReturn(existingExam);

        ResponseDTO responseDTO = examService.updateExam(EXAM_ID, examRequestDTO);

        assertNotNull(responseDTO.getData());
        assertEquals(existingExam, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveExamById() {
        when(examRepository.existsById(EXAM_ID)).thenReturn(true);

        ResponseDTO responseDTO = examService.removeExamById(EXAM_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(EXAM_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }
}
