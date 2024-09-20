package com.practice.p4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.p4.Entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
	
    Optional<Course> findByCourseName(String courseName);

}
