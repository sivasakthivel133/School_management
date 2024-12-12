package com.school.management.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentRequestDTO {
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String dob;
    private String joiningDate;
    private String gender;
    private String createdBy;
    private String updatedBy;
    private String schoolId;
    private String standardId;
}