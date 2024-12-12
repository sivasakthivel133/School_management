package com.school.management.controller;

import com.school.management.service.StandardFeeStructureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
public class StandardFeeStructureControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StandardFeeStructureService standardFeeStructureService;

    @InjectMocks
    private StandardFeeStructureController standardFeeStructureController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(standardFeeStructureController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateClassFeeStructure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/standard-fee-structure/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"classId\":\"1\",\"feeAmount\":1000,\"description\":\"Monthly Fee\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveClassFeeStructureById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/standard-fee-structure/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllClassFeeStructures() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/standard-fee-structure/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateClassFeeStructure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/standard-fee-structure/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"classId\":\"1\",\"feeAmount\":1200,\"description\":\"Updated Fee Structure\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveClassFeeStructureById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standard-fee-structure/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
