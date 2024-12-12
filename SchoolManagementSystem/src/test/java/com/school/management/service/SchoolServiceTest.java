package com.school.management.service;

import com.school.management.dto.ResponseDTO;
import com.school.management.dto.SchoolRequestDTO;
import com.school.management.entity.School;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.SchoolRepository;
import com.school.management.util.Constants;
import com.school.management.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SchoolServiceTest {

    @InjectMocks
    private SchoolService schoolService;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSchool() {
        School school = new School();
        school.setName("Test Name");
        school.setEmail("Test test@gmail.com");
        school.setLocation("Test location");
        school.setPhoneNo("9999900000");
        when(schoolRepository.save(any(School.class))).thenReturn(school);
        when(securityUtil.getCurrentUser()).thenReturn("super_admin");
        ResponseDTO responseDTO = schoolService.createSchool(new SchoolRequestDTO());

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), responseDTO.getStatusValue());
        assertEquals(Constants.CREATED, responseDTO.getMessage());
        verify(schoolRepository, times(1)).save(any(School.class));
    }

//    @Test
//    void testCreateSchoolValidationFailure() {
//        // Arrange
//        SchoolRequestDTO invalidRequestDTO = new SchoolRequestDTO(); // Missing fields
//
//        // Act & Assert
//        assertThrows(BadRequestServiceAlertException.class, () -> schoolService.createSchool(invalidRequestDTO));
//    }
//
//    @Test
//    public void testCreateSchool_WithSchoolNotFound() {
//
//        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
//        schoolRequestDTO.setName("null");
//        schoolRequestDTO.setLocation("chennai");
//        schoolRequestDTO.setEmail("siva@Gmail.com");
//        schoolRequestDTO.setPhoneNo("9999000099");
//
//        when(schoolRepository.findById("")).thenReturn(Optional.empty());
//        try {
//            ResponseDTO response = schoolService.createSchool(schoolRequestDTO);
//            fail();
//        } catch (BadRequestServiceAlertException exception) {
//            assertEquals(Constants.BAD, exception.getMessage());
//        }
//    }


    @Test
    void testRetrieveSchools() {
        when(schoolRepository.findAll()).thenReturn(null);
        ResponseDTO responseDTO = schoolService.retrieveSchools();
        assertEquals(Constants.RETRIEVED, responseDTO.getMessage());
        verify(schoolRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveSchoolById() {
        when(schoolRepository.findById("123")).thenReturn(Optional.empty());
        Exception exception = assertThrows(BadRequestServiceAlertException.class, () ->
                schoolService.retrieveSchoolById("123")
        );
        assertEquals(Constants.NOT_FOUND, exception.getMessage());
        verify(schoolRepository, times(1)).findById("123");
    }

    @Test
    void testUpdateSchoolById() {
        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
        schoolRequestDTO.setName("new name");

        School existingSchool = new School();
        existingSchool.setId("1");
        existingSchool.setName("new name");

        when(schoolRepository.findById("1")).thenReturn(Optional.of(existingSchool));
        when(schoolRepository.save(any(School.class))).thenReturn(existingSchool);

        ResponseDTO responseDTO = schoolService.updateSchool("1", schoolRequestDTO);
        assertEquals(Constants.UPDATED, responseDTO.getMessage());
        verify(schoolRepository, times(1)).findById("1");
        verify(schoolRepository, times(1)).save(existingSchool);
    }

//    @Test
//    public void testUpdateSchool_WithSchoolNotFound() {
//        SchoolRequestDTO schoolRequestDTO = new SchoolRequestDTO();
//        //schoolRequestDTO.set // Empty ID for testing
//        schoolRequestDTO.setName("null");
//        schoolRequestDTO.setLocation("Chennai");
//        schoolRequestDTO.setEmail("siva@gmail.com");
//        schoolRequestDTO.setPhoneNo("9999000099");
//
//        when(schoolRepository.findById("")).thenReturn(Optional.empty());
//
//        try {
//            schoolService.createSchool(schoolRequestDTO);
//            fail("Expected a BadRequestServiceAlertException to be thrown");
//        } catch (BadRequestServiceAlertException exception) {
//            assertEquals(Constants.NOT_FOUND, exception.getMessage());
//        }
//    }


    @Test
    void testRemoveSchoolById() {
        when(schoolRepository.existsById("1")).thenReturn(false);
        Exception exception = assertThrows(BadRequestServiceAlertException.class, () ->
                schoolService.removeSchoolById("1"));
        assertTrue(exception.getMessage().contains(Constants.NOT_FOUND));
        verify(schoolRepository, times(1)).existsById("1");
    }
}
