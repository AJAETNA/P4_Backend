package com.practice.p4.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practice.p4.Service.EmailService;
import com.practice.p4.pojo.EmailRequest;
import com.practice.p4.pojo.GenericResponse;
import com.practice.p4.Entity.*;
import com.practice.p4.Service.StudentService;
import com.practice.p4.pojo.StudentRequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

	@Autowired
	StudentService studentservice;
	
	@Autowired
    private EmailService emailService;
	
	@GetMapping("/student")
	public List<Student> show(){
		return this.studentservice.getAllStudents();
	}
	
	@PostMapping("/addstudent")
	public @ResponseBody GenericResponse addStudent(@RequestBody StudentRequestBody StudentRequestBody) {
		String msg = this.studentservice.addStudent(StudentRequestBody);
		GenericResponse gr = new GenericResponse(msg);
		return gr;
	}
	
	 // Update an existing student
    @PutMapping("updatestudent/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        // Check if student with given ID exists
        Optional<Student> existingStudent = studentservice.findStudentById(id);
        
        if (existingStudent.isPresent()) {
            student.setStudentId(id); // Ensure the correct ID is set
            Student updatedStudent = studentservice.updateStudent(student);
            return ResponseEntity.ok(updatedStudent);
        } else {
            // Return 404 if the student with the given ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return ResponseEntity.ok("Email sent successfully");
    }	
	
	@GetMapping("/getstudentbyid/{sId}")
	public Student getStudentByid(@PathVariable("sId") String id) {
		Student s = this.studentservice.getStudentById(Integer.parseInt(id));
		return s;
	}
	
	
	@DeleteMapping("/deletestudent/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") int studentId) {
		try {
			String result = studentservice.deleteStudent(studentId);
			return ResponseEntity.ok(result);
		} catch (RuntimeException e) {
			// Handle exceptions if student not found
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
	
	
	 @PostMapping("/savestudent")
	  public Student addStudent(@RequestBody Student student) {
	      return studentservice.saveStudent(student);
	  }
	
	 @GetMapping("/getstudentcode/{sId}")
	 public String getstudentcode(@PathVariable("sId") int studentId) {
		 String code = this.studentservice.getNextStudentCode();
		 return code;
	 }
	 
	 @GetMapping("/coursesbystudentid/{studentId}")
	    public List<Course> getCoursesByStudentId(@PathVariable int studentId) {
	        return studentservice.getCoursesByStudentId(studentId);
	    }

	    @GetMapping("/departmentsbystudentid/{studentId}")
	    public List<Department> getDepartmentsByStudentId(@PathVariable int studentId) {
	        return studentservice.getDepartmentsByStudentId(studentId);
	    }
	    
	    @GetMapping("/details/{studentId}")
	    public ResponseEntity<Map<String, Object>> getStudentDetails(@PathVariable("studentId") int studentId) {
	        Map<String, Object> studentDetails = studentservice.getStudentDetailsById(studentId);
	        return ResponseEntity.ok(studentDetails);
	    }
	    
	    @GetMapping("/generateStudentCode")
	    public ResponseEntity<String> generateStudentCode() {
	        String newStudentCode = studentservice.getNextStudentCode();
	        return ResponseEntity.ok(newStudentCode);
	    }
}
