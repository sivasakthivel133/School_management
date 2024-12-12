package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SubjectRequestDTO;
import com.school.management.entity.Standard;
import com.school.management.entity.Subject;
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

public class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String SUBJECT_ID = "subject-1";
    private static final String STANDARD_ID = "class-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubject() {
        SubjectRequestDTO subjectRequestDTO = new SubjectRequestDTO();
        subjectRequestDTO.setStandardId(STANDARD_ID);
        subjectRequestDTO.setName("Mathematics");

        Standard standard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));

        Subject subject = mock(Subject.class);
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        ResponseDTO responseDTO = subjectService.createSubject(subjectRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(subject, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void testRetrieveAllSubjects() {
        Subject subject = mock(Subject.class);

        List<Subject> subjectList = Stream.ofNullable(subject).toList();
        when(subjectRepository.findAll()).thenReturn(subjectList);

        ResponseDTO responseDTO = subjectService.retrieveAllSubjects();

        assertNotNull(responseDTO.getData());
        assertEquals(subjectList, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveSubjectById() {
        Subject subject = mock(Subject.class);

        when(subjectRepository.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));
        ResponseDTO responseDTO = subjectService.retrieveSubjectById(SUBJECT_ID);

        Object data = responseDTO.getData();
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateSubject() {
        Subject subject = mock(Subject.class);
        SubjectRequestDTO subjectRequestDTO = mock(SubjectRequestDTO.class);

        when(subjectRepository.findById(SUBJECT_ID)).thenReturn(Optional.of(subject));
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        ResponseDTO responseDTO = subjectService.updateSubject(SUBJECT_ID, subjectRequestDTO);

        assertEquals(subject, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveSubjectById() {
        when(subjectRepository.existsById(SUBJECT_ID)).thenReturn(true);

        ResponseDTO responseDTO = subjectService.removeSubjectById(SUBJECT_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(SUBJECT_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }
}
