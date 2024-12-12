package com.school.management.repository;

import com.school.management.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s " +
            "WHERE (:search IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))) OR " +
            "(:search IS NULL OR LOWER(s.address) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Student> searchStudents(@Param("search") String name, Pageable pageable);
}
