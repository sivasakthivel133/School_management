package com.school.management.controller;

import com.school.management.service.StandardService;
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
public class StandardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StandardService standardService;

    @InjectMocks
    private StandardController standardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(standardController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateClass() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/standard/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grade 1\",\"description\":\"Primary level class\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveClassById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/standard/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllClasses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/standard/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateClass() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/standard/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Grade 2\",\"description\":\"Updated primary level class\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveClassById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standard/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

