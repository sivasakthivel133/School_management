package com.school.management.repository;

import com.school.management.entity.TeacherAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherAttendanceRepository extends JpaRepository<TeacherAttendance,String> {
}
