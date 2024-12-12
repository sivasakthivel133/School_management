package com.school.management.dto;

import lombok.Data;

@Data
public class SectionRequestDTO {
    private String name;
    private String createdBy;
    private String updatedBy;
    private String classId;
}