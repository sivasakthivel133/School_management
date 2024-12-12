package com.school.management.dto;

import lombok.Data;

@Data
public class SubjectRequestDTO {
    private String standardId;
    private String name;
    private String createdBy;
    private String updatedBy;
}