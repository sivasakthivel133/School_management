package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StandardRequestDTO;
import com.school.management.entity.School;
import com.school.management.entity.Standard;
import com.school.management.repository.SchoolRepository;
import com.school.management.repository.StandardRepository;
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

public class StandardServiceTest {

    @InjectMocks
    private StandardService standardService;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String SCHOOL_ID = "school-1";
    private static final String STANDARD_ID = "standard-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClass() {
        StandardRequestDTO standardRequestDTO = new StandardRequestDTO();
        standardRequestDTO.setSchoolId(SCHOOL_ID);
        standardRequestDTO.setName("standard 10");

        School school = mock(School.class);
        when(schoolRepository.findById(SCHOOL_ID)).thenReturn(Optional.of(school));

        Standard standard = mock(Standard.class);
        when(standardRepository.save(any(Standard.class))).thenReturn(standard);

        ResponseDTO responseDTO = standardService.createClass(standardRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(standard, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(standardRepository, times(1)).save(any(Standard.class));
    }

    @Test
    void testRetrieveAllClasses() {
        Standard standard = mock(Standard.class);

        List<Standard> standardList = Stream.ofNullable(standard).toList();
        when(standardRepository.findAll()).thenReturn(standardList);

        ResponseDTO responseDTO = standardService.retrieveAllClasses();

        assertNotNull(responseDTO.getData());
        assertEquals(standardList, responseDTO.getData());
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveClassById() {
        Standard standard = mock(Standard.class);

        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.ofNullable(standard));
        ResponseDTO responseDTO = standardService.retrieveClassById(STANDARD_ID);

        Object data = responseDTO.getData();
        assertNotNull(responseDTO.getData());
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateClass() {
        Standard standard = mock(Standard.class);
        StandardRequestDTO standardDTO = mock(StandardRequestDTO.class);

        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.ofNullable(standard));
        when(standardRepository.save(any(Standard.class))).thenReturn(standard);

        ResponseDTO responseDTO = standardService.updateClass(STANDARD_ID, standardDTO);

        assertNotNull(responseDTO);
        assertEquals(standard, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveClass() {
        when(standardRepository.existsById(STANDARD_ID)).thenReturn(true);

        ResponseDTO responseDTO = standardService.removeClassById(STANDARD_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(STANDARD_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }
}
