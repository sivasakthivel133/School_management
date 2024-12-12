package com.school.management.dto;

import lombok.Data;

@Data
public class TeacherRequestDTO {
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String gender;
    private String createdBy;
    private String updatedBy;
    private String schoolId;
}