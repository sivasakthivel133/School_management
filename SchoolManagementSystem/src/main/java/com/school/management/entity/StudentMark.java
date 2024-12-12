package com.school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_mark")
public class StudentMark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "mark1")
    private int mark1;

    @Column(name = "mark2")
    private int mark2;

    @Column(name = "mark3")
    private int mark3;

    @Column(name = "mark4")
    private int mark4;

    @Column(name = "mark5")
    private int mark5;

    @Column(name = "total")
    private int total;

    @Column(name = "created_by")
    private String createdBy;

    @CurrentTimestamp
    private Instant createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Exam exam;

    @ManyToOne
    @JoinColumn(name="section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

}