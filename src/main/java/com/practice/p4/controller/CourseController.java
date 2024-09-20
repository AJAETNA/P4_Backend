package com.practice.p4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.p4.Entity.Course;
import com.practice.p4.Service.CourseService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
	
	@Autowired
    private CourseService courseService;

    // Get all courses
    @GetMapping("allcourses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Get course by ID
    @GetMapping("getcoursebyId/{courseId}")
    public Course getCourseById(@PathVariable int courseId) {
        return courseService.getCourseById(courseId);
    }
	
	// Add a new course
    @PostMapping("addcourse/add")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    // Update an existing course
    @PutMapping("/updatecourse/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }
    
    @DeleteMapping("deletecourse/{courseId}")
    public void deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
    }
}
