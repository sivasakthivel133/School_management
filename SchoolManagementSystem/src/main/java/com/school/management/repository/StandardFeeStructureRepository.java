package com.school.management.repository;

import com.school.management.entity.StandardFeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardFeeStructureRepository extends JpaRepository<StandardFeeStructure,String> {
}
