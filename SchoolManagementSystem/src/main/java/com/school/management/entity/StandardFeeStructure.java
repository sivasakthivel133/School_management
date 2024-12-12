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
@Table(name = "standard_fee_structure")
public class StandardFeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "fee_amount")
    private float feeAmount;

    @CurrentTimestamp
    private Instant createdAt;

    @Column(name = "created-by")
    private String createdBy;

    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    private Standard standard;
}