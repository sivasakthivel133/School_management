package com.school.management.controller;

import com.school.management.service.StudentService;
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
public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testCreateStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"age\":15,\"class\":\"10th Grade\",\"email\":\"johndoe@gmail.com\",\"phone\":\"9876543210\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveAllStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/retrieve"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRetrieveStudentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/retrieve/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testUpdateStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/student/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe Updated\",\"age\":16,\"class\":\"11th Grade\",\"email\":\"johndoe_updated@gmail.com\",\"phone\":\"9876543211\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testRemoveStudentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/remove/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_SUPER_ADMIN"})
    void testSearchStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/search")
                        .param("search", "John")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortField", "name")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
