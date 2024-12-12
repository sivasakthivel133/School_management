package com.school.management.dto;

import lombok.Data;

@Data
public class StudentAttendanceRequestDTO {
    public String studentId;
    private String status;
    private String reason;
    private String date;
    private String createdBy;
    private String updatedBy;
}