package com.school.management.repository;

import com.school.management.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface
SchoolRepository extends JpaRepository<School, String> {

    @Query("SELECT s FROM School s " +
            "WHERE (:search IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))) OR " +
            "(:search IS NULL OR LOWER(s.location) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<School> searchSchool(@Param("search") String name, Pageable pageable);

    Optional<School> findByEmail(String email);

    Optional<School> findByPhoneNo(String phoneNo);
}

