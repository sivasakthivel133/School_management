package com.school.management.controller;

import com.school.management.dto.ResponseDTO;
import com.school.management.service.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
public class SchoolControllerTest {


    private MockMvc mockMvc;

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private SchoolController schoolController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testCreateSchool() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/school/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\" ABC school\",\"location\":\"chennai\",\"email\":\"abc@gmail.com\",\"phoneNo\":\"1234567890\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
//    void testCreateSchoolAccessDenied() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/school/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\" ABC school\",\"location\":\"chennai\",\"email\":\"abc@gmail.com\",\"phoneNo\":\"1234567890\"}"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @Test
//    void testCreateSchool_WithSchoolNotFound()
//    {
//        ResponseDTO responseDTO=schoolService.createSchool(null);
//        assertNotNull(responseDTO);
//        assertEquals(HttpStatus.BAD_REQUEST.name(),responseDTO.getStatusValue());
//        assertEquals("school dto is null",responseDTO.getMessage());
//    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveSchools() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/school/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveSchoolById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/school/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateSchool() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/school/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Springfield Updated\",\"location\":\"Springfield City\",\"email\":\"updated@school.com\",\"phoneNo\":\"9876543210\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    void testUpdateSchool_WithSchoolIdNotFound()
//    {
//        ResponseDTO responseDTO=schoolService.updateSchool(null);
//        assertNotNull(responseDTO);
//        assertEquals(HttpStatus.BAD_REQUEST.name(),responseDTO.getStatusValue());
//        assertEquals("school dto is null",responseDTO.getMessage());
//    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveSchoolById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/school/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testSearchSchools() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/school/search")
                        .param("search", "Springfield")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortField", "name")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}