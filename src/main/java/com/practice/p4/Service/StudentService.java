package com.practice.p4.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.p4.Service.EmailService;
import com.practice.p4.Entity.*;
import com.practice.p4.pojo.StudentRequestBody;
import com.practice.p4.repository.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
	
	@Autowired
	StudentRepo studentrepo;
	
	@Autowired
	private EmailService emailService;

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private CourseRepo courseRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private StudentDetailsRepo studentDetailsRepository;
	
	// Fetch last student code
    public String getNextStudentCode() {
        String lastStudentCode = studentrepo.findTopByOrderByStudentCodeDesc();
        if (lastStudentCode != null) {
            // Extract the numeric part, increment by 1, and return new code
            int codeNumber = Integer.parseInt(lastStudentCode.substring(2));
            return String.format("ST%04d", codeNumber + 1);
        } else {
            // If no students, start with ST0001
            return "ST0001";
        }
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Student getStudentById(int studentId) {
        Student student =  studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        
        // Create a StudentResponse object with student details
        Student response = new Student();
        response.setStudentId(student.getStudentId());
        response.setStudentName(student.getStudentName());
        response.setsCode(student.getsCode());
        response.setsUserName(student.getsUserName());
        response.setsDate(student.getsDate());
        
        
        // Set details
        response.setDetails(student.getDetails().stream()
            .map(sd -> new StudentDetails(
                sd.getStudentDetailsId(),
                response, sd.getCourseId(),
                sd.getDeptId(),
                sd.getsAddress(),
                sd.getMobileNo()
            ))
            .toList());

        return response;
    }
    
    @Transactional
    public String addStudent(StudentRequestBody studentRequestBody) {
        List<StudentDetails> studentDetailsList = new ArrayList<>();
        
        for (StudentDetails detailsRequest : studentRequestBody.getDetails()) {
            StudentDetails sd = new StudentDetails();
            
            sd.setsAddress(detailsRequest.getsAddress());
            sd.setMobileNo(detailsRequest.getMobileNo());
            sd.setCourseId(detailsRequest.getCourseId());
            sd.setDeptId(detailsRequest.getDeptId());
            sd.setStudent(detailsRequest.getStudent());
            sd.setStudentDetailsId(detailsRequest.getStudentDetailsId());

            studentDetailsList.add(sd);
        }

        Student student = new Student(
            studentRequestBody.getStudentName(),
            studentRequestBody.getsUserName(),
            studentRequestBody.getsDate(),
            studentDetailsList
        );

        student.setsCode(getNextStudentCode());

        for (StudentDetails sd : studentDetailsList) {
            sd.setStudent(student);  // Set relationship
        }

        studentrepo.save(student);
        
        // Send email notification
//        emailService.sendEmail(
//        	"chetanapathadiya3@gmail.com",
//            "New Student Added",
//            "A new student " + student.getStudentName() + " has been added successfully."
//        );
        
        return "Student Added Successfully";
    }
    

	@Transactional
    public Student saveStudent(Student student) {
        // Save the student entity
        Student savedStudent = studentrepo.save(student);
        
        // Iterate through student details and set the student reference
        for (StudentDetails detail : student.getDetails()) {
            detail.setStudent(savedStudent);
        }
        
        savedStudent.setsCode(getNextStudentCode());

        // Save the details separately
        studentDetailsRepository.saveAll(student.getDetails());
        
//        emailService.sendEmail(
//    	        "chetanapathadiya3@gmail.com", // Recipient's email
//    	        "New Student Added", 
//    	        "A new student " + savedStudent.getStudentName() + " has been added successfully."
//    	    );

        return savedStudent;
    }
	
	 public Student updateStudent(Student student) {
		    // Retrieve the existing student by ID
		    Optional<Student> existingStudentOpt = studentrepo.findById(student.getStudentId());
		    if (existingStudentOpt.isPresent()) {
		        Student existingStudent = existingStudentOpt.get();

		        // Update student fields
		        existingStudent.setStudentName(student.getStudentName());
		        existingStudent.setsUserName(student.getsUserName());
		       
		        
		        // Update or add details
		        List<StudentDetails> updatedDetails = student.getDetails();
		        
		        // Remove old details that are not present in the updated details
		        List<StudentDetails> existingDetails = new ArrayList<>(existingStudent.getDetails());
		        for (StudentDetails existingDetail : existingDetails) {
		            if (!updatedDetails.contains(existingDetail)) {
		                existingStudent.getDetails().remove(existingDetail);
		                studentDetailsRepository.delete(existingDetail);
		            }
		        }
		        
		        // Add or update details
		        for (StudentDetails detail : updatedDetails) {
		            detail.setStudent(existingStudent); // Set bidirectional relationship
		            if (detail.getStudentDetailsId() == 0) {
		                // New detail
		                existingStudent.getDetails().add(detail);
		            } else {
		                // Existing detail
		            	studentDetailsRepository.save(detail);
		            }
		        }
		        
		        // Save the updated student entity
		        Student updatedStudent = studentrepo.save(existingStudent);

		        // Send email notification
//		        emailService.sendEmail(
//		            "chetanapathadiya3@gmail.com", // Recipient's email
//		            "Student Updated", 
//		            "The student " + updatedStudent.getStudentName() + " has been updated successfully."
//		        );

		        return updatedStudent;
		    } else {
		        // Handle the case where the student does not exist
		        throw new EntityNotFoundException("Student with ID " + student.getStudentId() + " not found.");
		    }
		}
	 
	  @Transactional
	    public String deleteStudent(int studentId) {
	        // Fetch the student by ID
	        Student student = studentrepo.findById(studentId)
	            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
	        
	        // Delete the student (cascades to delete associated StudentDetails if cascade is configured)
	        studentrepo.delete(student);
	        
	        return "Student deleted successfully";
	    }

	  public Optional<Student> findStudentById(int id) {
	        return studentrepo.findById(id);
	    }

	  
	  public List<Course> getCoursesByStudentId(int studentId) {
	        Student student = studentRepository.findById(studentId)
	                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

	        return student.getDetails().stream()
	                .map(detail -> courseRepository.findById(detail.getCourseId())
	                        .orElseThrow(() -> new RuntimeException("Course not found with ID: " + detail.getCourseId())))
	                .collect(Collectors.toList());
	    }
	  
	    public List<Department> getDepartmentsByStudentId(int studentId) {
	        Student student = studentRepository.findById(studentId)
	                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

	        return student.getDetails().stream()
	                .map(detail -> departmentRepository.findById(detail.getDeptId())
	                        .orElseThrow(() -> new RuntimeException("Department not found with ID: " + detail.getDeptId())))
	                .collect(Collectors.toList());
	    }
	    
//	    public Student getStudentByIdWithDetails(int studentId) {
//	        Student student = studentRepository.findById(studentId)
//	                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
//
//	        List<Course> courses = student.getDetails().stream()
//	                .map(detail -> courseRepository.findById(detail.getCourseId())
//	                        .orElseThrow(() -> new RuntimeException("Course not found with ID: " + detail.getCourseId())))
//	                .collect(Collectors.toList());
//
//	        List<Department> departments = student.getDetails().stream()
//	                .map(detail -> departmentRepository.findById(detail.getDeptId())
//	                        .orElseThrow(() -> new RuntimeException("Department not found with ID: " + detail.getDeptId())))
//	                .collect(Collectors.toList());
//
//	        student.setCourses(courses); // Add a list of courses to the student
//	        student.setDepartments(departments); // Add a list of departments to the student
//
//	        return student;
//	    }
	    
//	    public Map<String, String> getCourseNameAndDepartmentNameById(int studentId) {
//	        List<Object[]> results = studentDetailsRepository.findCourseAndDepartmentNameByStudentIdNative(studentId);
//	        Map<String, String> details = new HashMap<>();
//	        
//	        for (Object[] result : results) {
//	            // Assuming the order of columns is: student_id, student_details_id, mobile_no, s_address, course_id, dept_id, dept_name, course_name
//	            // Cast each element to the expected type based on the query
//	            String courseName = (String) result[7]; // Adjust index based on the column order
//	            String departmentName = (String) result[6]; // Adjust index based on the column order
//	            
//	            details.put("courseName", courseName);
//	            details.put("departmentName", departmentName);
//	        }
//
//	        return details;
//	    }
	    
	    public Map<String, Object> getStudentDetailsById(int studentId) {
	        List<Object[]> result = studentRepository.findStudentDetailsById(studentId);
	        
	        if (result.isEmpty()) {
	            throw new RuntimeException("Student not found for ID: " + studentId);
	        }

	        // Map to hold student details
	        Map<String, Object> studentDetails = new HashMap<>();
	        
	        // List to store multiple course and department details
	        List<Map<String, Object>> courseAndDeptDetails = new ArrayList<>();
	        
	        for (Object[] row : result) {
	            if (studentDetails.isEmpty()) {
	                // Populate student information only once
	                studentDetails.put("studentId", row[0]);
	                studentDetails.put("sCode", row[1]);
	                studentDetails.put("studentName", row[2]);
	                studentDetails.put("sUserName", row[3]);
	                studentDetails.put("sDate", row[4]);
	            }
	            
	            // Add course and department info for each student detail entry
	            Map<String, Object> courseDept = new HashMap<>();
	            courseDept.put("sAddress", row[5]);
	            courseDept.put("mobileNo", row[6]);
	            courseDept.put("courseId", row[7]);         // Added courseId
	            courseDept.put("courseName", row[8]);
	            courseDept.put("deptId", row[9]);           // Added deptId
	            courseDept.put("deptName", row[10]);
	            
	            courseAndDeptDetails.add(courseDept);
	        }
	        
	        // Add the course and department details list to the response
	        studentDetails.put("details", courseAndDeptDetails);
	        
	        return studentDetails;
	    }
}
