package com.school.management.controller;

import com.school.management.service.StudentMarkService;
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
public class StudentMarkControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentMarkService studentMarkService;

    @InjectMocks
    private StudentMarkController studentMarkController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentMarkController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateStudentMark() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/student-mark/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":\"1\",\"examId\":\"12\",\"mark1\":90,\"mark2\":89,\"mark3\":88,\"mark4\":99,\"mark5\":77,\"total\":450}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllStudentMarks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student-mark/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveStudentMarkById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student-mark/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateStudentMark() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/student-mark/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":\"1\",\"examId\":\"12\",\"mark1\":90,\"mark2\":89,\"mark3\":88,\"mark4\":99,\"mark5\":77,\"total\":450}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveStudentMarkById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student-mark/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testStudentMarkList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student-mark/list")
                        .param("section", "A")
                        .param("subject", "Math")
                        .param("teacher", "Mr. Smith")
                        .param("range", "80")
                        .param("isGreaterThen", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testGetStudentMarksKeyValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student-mark/key-value"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
