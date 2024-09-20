package com.practice.p4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practice.p4.Entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
	
	 // Correct this method to return the student_code instead of the whole entity
    @Query(value = "SELECT s.s_code FROM student s ORDER BY s.s_code DESC LIMIT 1", nativeQuery = true)
    String findTopByOrderByStudentCodeDesc();
    
    boolean existsBysUserName(String sUserName);
    
    @Query(value = "SELECT s.student_id, s.s_code, s.student_name, s.s_user_name, s.s_date, " +
            "sd.s_address, sd.mobile_no, sd.course_id, c.course_name, sd.dept_id, d.dept_name " +
            "FROM student s " +
            "JOIN student_details sd ON s.student_id = sd.student_id " +
            "JOIN course c ON sd.course_id = c.course_id " +
            "JOIN department d ON sd.dept_id = d.dept_id " +
            "WHERE s.student_id = :studentId", nativeQuery = true)
    List<Object[]> findStudentDetailsById(@Param("studentId") int studentId);
}
