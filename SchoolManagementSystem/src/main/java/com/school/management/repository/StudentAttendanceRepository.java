package com.school.management.repository;

import com.school.management.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance,String> {
}
