package com.school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "student_attendance")
public class StudentAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "date")
    private String date;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @CurrentTimestamp
    private Instant createdAt;

    @Column(name = "created-by")
    private String createdBy;

    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "updated-by")
    private String updatedBy;

    @ManyToOne
    private Student student;
}