package com.school.management.controller;

import com.school.management.service.SubjectService;
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
public class SubjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateSubject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mathematics\",\"description\":\"Advanced Math subject\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllSubjects() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveSubjectById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateSubject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mathematics Updated\",\"description\":\"Updated description for Math subject\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveSubjectById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
