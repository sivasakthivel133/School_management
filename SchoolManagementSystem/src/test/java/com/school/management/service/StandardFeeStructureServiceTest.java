package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.StandardFeeStructureRequestDTO;
import com.school.management.entity.Standard;
import com.school.management.entity.StandardFeeStructure;
import com.school.management.repository.StandardFeeStructureRepository;
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

public class StandardFeeStructureServiceTest {

    @InjectMocks
    private StandardFeeStructureService standardFeeStructureService;

    @Mock
    private StandardFeeStructureRepository standardFeeStructureRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String STANDARD_ID = "standard-1";
    private static final String FEE_STRUCTURE_ID = "fee-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClassFeeStructure() {
        StandardFeeStructureRequestDTO requestDTO = new StandardFeeStructureRequestDTO();
        requestDTO.setClassId(STANDARD_ID);
        requestDTO.setFeeAmount(10000);

        Standard standard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));
        when(securityUtil.getCurrentUser()).thenReturn("user1");

        StandardFeeStructure feeStructure = mock(StandardFeeStructure.class);
        when(standardFeeStructureRepository.save(any(StandardFeeStructure.class))).thenReturn(feeStructure);

        ResponseDTO responseDTO = standardFeeStructureService.createClassFeeStructure(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(feeStructure, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(standardFeeStructureRepository, times(1)).save(any(StandardFeeStructure.class));
    }

    @Test
    void testRetrieveClassFeeStructureById() {
        StandardFeeStructure feeStructure = mock(StandardFeeStructure.class);

        when(standardFeeStructureRepository.findById(FEE_STRUCTURE_ID)).thenReturn(Optional.of(feeStructure));

        ResponseDTO responseDTO = standardFeeStructureService.retrieveClassFeeStructureById(FEE_STRUCTURE_ID);

        assertNotNull(responseDTO);
        assertEquals(feeStructure, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
    }

    @Test
    void testRetrieveAllClassFeeStructures() {
        StandardFeeStructure feeStructure = mock(StandardFeeStructure.class);
        List<StandardFeeStructure> feeStructures = Stream.ofNullable(feeStructure).toList();

        when(standardFeeStructureRepository.findAll()).thenReturn(feeStructures);

        ResponseDTO responseDTO = standardFeeStructureService.retrieveAllClassFeeStructures();

        assertNotNull(responseDTO);
        assertEquals(feeStructures, responseDTO.getData());
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateClassFeeStructure() {
        StandardFeeStructure existingFeeStructure = mock(StandardFeeStructure.class);
        StandardFeeStructureRequestDTO requestDTO = new StandardFeeStructureRequestDTO();
        requestDTO.setFeeAmount(12000);
        requestDTO.setUpdatedBy("admin");

        when(standardFeeStructureRepository.findById(FEE_STRUCTURE_ID)).thenReturn(Optional.of(existingFeeStructure));
        when(standardFeeStructureRepository.save(any(StandardFeeStructure.class))).thenReturn(existingFeeStructure);

        ResponseDTO responseDTO = standardFeeStructureService.updateClassFeeStructure(FEE_STRUCTURE_ID, requestDTO);

        assertNotNull(responseDTO);
        assertEquals(existingFeeStructure, responseDTO.getData());
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveClassFeeStructureById() {
        when(standardFeeStructureRepository.existsById(FEE_STRUCTURE_ID)).thenReturn(true);

        ResponseDTO responseDTO = standardFeeStructureService.removeClassFeeStructureById(FEE_STRUCTURE_ID);

        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(FEE_STRUCTURE_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.getReasonPhrase(), responseDTO.getStatusValue());
        verify(standardFeeStructureRepository, times(1)).existsById(FEE_STRUCTURE_ID);
    }
}
