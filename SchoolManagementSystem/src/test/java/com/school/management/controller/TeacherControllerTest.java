package com.school.management.controller;

import com.school.management.service.TeacherService;
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
public class TeacherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"subject\":\"Math\",\"email\":\"john.doe@gmail.com\",\"phoneNo\":\"9876543210\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllTeachers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveTeacherById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/teacher/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Updated\",\"subject\":\"Science\",\"email\":\"john.updated@gmail.com\",\"phoneNo\":\"1234567890\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveTeacherById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teacher/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
