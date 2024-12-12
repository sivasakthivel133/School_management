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
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "dob")
    private String  dob;

    @Column(name = "joining_date")
    private String joiningDate;

    @Column(name = "gender")
    private String gender;

    @CurrentTimestamp
    private Instant createdAt;

    @Column(name = "created-by")
    private String createdBy;

    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "updated-by")
    private String updatedBy;

    @ManyToOne
    private School school;

    @ManyToOne
    private Standard standard;
}