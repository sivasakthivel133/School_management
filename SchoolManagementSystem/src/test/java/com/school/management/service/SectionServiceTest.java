package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SectionRequestDTO;
import com.school.management.entity.Section;
import com.school.management.entity.Standard;
import com.school.management.repository.SectionRepository;
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

public class SectionServiceTest {

    @InjectMocks
    private SectionService sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private SecurityUtil securityUtil;

    private static final String SECTION_ID = "section-1";
    private static final String STANDARD_ID = "standard-1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSection() {
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setClassId(STANDARD_ID);
        sectionRequestDTO.setName("Section A");

        Standard standard = mock(Standard.class);
        when(standardRepository.findById(STANDARD_ID)).thenReturn(Optional.of(standard));

        Section section = mock(Section.class);
        when(sectionRepository.save(any(Section.class))).thenReturn(section);

        when(securityUtil.getCurrentUser()).thenReturn("admin");

        ResponseDTO responseDTO = sectionService.createSection(sectionRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(section, responseDTO.getData());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        verify(sectionRepository, times(1)).save(any(Section.class));
    }

    @Test
    void testRetrieveAllSections() {
        Section section = mock(Section.class);

        List<Section> sectionList = Stream.ofNullable(section).toList();
        when(sectionRepository.findAll()).thenReturn(sectionList);

        ResponseDTO responseDTO = sectionService.retrieveAllSections();

        assertNotNull(responseDTO.getData());
        assertEquals(responseDTO.getData(), sectionList);
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRetrieveSectionById() {
        Section section = mock(Section.class);

        when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(section));
        ResponseDTO responseDTO = sectionService.retrieveSectionById(SECTION_ID);

        Object data = responseDTO.getData();
        assertNotNull(responseDTO.getData());
        assertEquals(data, responseDTO.getData());
        assertEquals(Constants.SUCCESS, responseDTO.getMessage());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testUpdateSection() {
        Section existingSection = mock(Section.class);
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO();
        sectionRequestDTO.setName("Updated Section A");
        sectionRequestDTO.setUpdatedBy("admin");

        when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(existingSection));
        when(sectionRepository.save(any(Section.class))).thenReturn(existingSection);

        ResponseDTO responseDTO = sectionService.updateSection(SECTION_ID, sectionRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        assertEquals(existingSection, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
    }

    @Test
    void testRemoveSectionById() {
        when(sectionRepository.existsById(SECTION_ID)).thenReturn(true);

        ResponseDTO responseDTO = sectionService.removeSectionById(SECTION_ID);

        assertNotNull(responseDTO);
        assertEquals(Constants.DELETED, responseDTO.getMessage());
        assertEquals(SECTION_ID, responseDTO.getData());
        assertEquals(HttpStatus.OK.name(), responseDTO.getStatusValue());
        verify(sectionRepository, times(1)).existsById(SECTION_ID);
    }
}
