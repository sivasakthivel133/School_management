package com.school.management.dto;

import lombok.Data;

@Data
public class TeacherAttendanceRequestDTO {
    private String date;
    private String status;
    private String createdBy;
    private String updatedBy;
    private String teacherId;
}