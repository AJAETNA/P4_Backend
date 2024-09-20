package com.practice.p4.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.p4.Entity.Course;
import com.practice.p4.repository.CourseRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
	
	  @Autowired
	    private CourseRepo courseRepository;

	    // Get all courses
	    public List<Course> getAllCourses() {
	        return courseRepository.findAll();
	    }

	    // Get course by ID
	    public Course getCourseById(int courseId) {
	        return courseRepository.findById(courseId)
	                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
	    }

	    public Course addCourse(Course course) {
	        // Ensure that the course does not have an ID yet (new course)
	        if (course.getCourseId() != 0) {
	            throw new IllegalArgumentException("New course cannot already have an ID");
	        }
	        return courseRepository.save(course);
	    }
	    
	 // Update an existing course
	    public Course updateCourse(int id, Course updatedCourse) {
	        // Check if the course exists
	        Course existingCourse = courseRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + id + " not found"));

	        // Update the fields of the existing course
	        existingCourse.setCourseName(updatedCourse.getCourseName());
	        existingCourse.setCduration(updatedCourse.getCduration());

	        // Save and return the updated course
	        return courseRepository.save(existingCourse);
	    }


	    // Delete course by ID
	    public void deleteCourse(int courseId) {
	        if (!courseRepository.existsById(courseId)) {
	            throw new RuntimeException("Course not found with ID: " + courseId);
	        }
	        courseRepository.deleteById(courseId);
	    }
}
