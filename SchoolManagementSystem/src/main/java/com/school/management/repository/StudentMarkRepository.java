package com.school.management.repository;

import com.school.management.entity.StudentMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMarkRepository extends JpaRepository<StudentMark, String> {

    @Query(value = "SELECT  sec.name AS section_name,sub.id AS subject_id, sub.name AS subject_name, t.name AS teacher_name,t.id AS teacher_id,sm.total\n" +
            " FROM fyndus.student_mark sm\n" +
            "  JOIN fyndus.section sec  \n" +
            "  JOIN fyndus.subject sub \n" +
            "  JOIN fyndus.teacher t \n" +
            "  WHERE sec.name='A sec' AND \n" +
            "   sub.name='tamil' AND \n" +
            "   t.name='mohan' AND\n" +
            "   (sm.total/5 >= 80);", nativeQuery = true)
    List<Object[]> studentMarkList();
}