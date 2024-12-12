package com.school.management.dto;

import lombok.Data;

@Data
public class ExamRequestDTO {
    private String name;
    private String type;
    private String createdBy;
    private String updatedBy;
    private String classId;
    private String subjectId;
}
