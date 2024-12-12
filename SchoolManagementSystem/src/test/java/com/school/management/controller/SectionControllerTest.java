package com.school.management.controller;

import com.school.management.service.SectionService;
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
public class SectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SectionService sectionService;

    @InjectMocks
    private SectionController sectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sectionController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateSection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/section/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Section A\",\"standardId\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveSectionById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/section/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllSections() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/section/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateSection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/section/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Section B Updated\",\"standardId\":\"2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveSectionById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/section/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
