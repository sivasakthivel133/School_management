package com.school.management.controller;

import com.school.management.service.TeacherAttendanceService;
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
public class TeacherAttendanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeacherAttendanceService teacherAttendanceService;

    @InjectMocks
    private TeacherAttendanceController teacherAttendanceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(teacherAttendanceController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateAttendance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teacher-attendance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teacherId\":\"1\",\"date\":\"2024-12-06\",\"status\":\"Present\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllAttendance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher-attendance/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAttendanceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher-attendance/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateAttendance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/teacher-attendance/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teacherId\":\"1\",\"date\":\"2024-12-06\",\"status\":\"Absent\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveAttendanceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teacher-attendance/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
