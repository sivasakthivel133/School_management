package com.school.management.dto;

import lombok.Data;

@Data
public class StandardFeeStructureRequestDTO {
    private float feeAmount;
    private String createdBy;
    private String updatedBy;
    private String classId;
}