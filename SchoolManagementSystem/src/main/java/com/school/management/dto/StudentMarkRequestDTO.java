package com.school.management.dto;

import com.school.management.entity.Section;
import com.school.management.entity.Subject;
import com.school.management.entity.Teacher;
import lombok.Data;

@Data
public class StudentMarkRequestDTO {
    private int mark1;
    private int mark2;
    private int mark3;
    private int mark4;
    private int mark5;
    private int total;
    private String createdBy;
    private String updatedBy;
    private String studentId;
    private String examId;
    private int range;
    private Boolean isGreaterThan;

    private Section section;
    private Subject subject;
    private Teacher teacher;
}
