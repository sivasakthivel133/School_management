package com.school.management.controller;

import com.school.management.service.ExamService;
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
public class ExamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void testCreateExam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\" public Exam\",\"subject\":\"Maths\",\"date\":\"2024-12-10\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "admin")
    void testRetrieveExamById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exam/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "admin")
    void testRetrieveAllExams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exam/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "admin")
    void testUpdateExam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/exam/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Final Term Exam\",\"subject\":\"Science\",\"date\":\"2024-12-20\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "admin")
    void testRemoveExamById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/exam/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
