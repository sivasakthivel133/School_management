package com.school.management.dto;

import lombok.Data;

@Data
public class SchoolRequestDTO {
    private String name;
    private String location;
    private String email;
    private String phoneNo;
    private String createdBy;
    private String updatedBy;
}
