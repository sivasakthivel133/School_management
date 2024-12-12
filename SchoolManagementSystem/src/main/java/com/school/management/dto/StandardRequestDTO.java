package com.school.management.dto;

import lombok.Data;

@Data
public class StandardRequestDTO {
    private String name;
    private String createdBy;
    private String updatedBy;
    private String schoolId;
}