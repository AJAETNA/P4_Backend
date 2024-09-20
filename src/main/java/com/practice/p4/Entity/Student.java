package com.practice.p4.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	private String sCode;
	private String studentName;
	private String sUserName;
	private String sDate;
	
	@OneToMany(mappedBy="student",cascade=CascadeType.ALL, orphanRemoval = true,fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<StudentDetails> details = new ArrayList<>();
	
	@Transient
    private List<Course> courses;

    @Transient
    private List<Department> departments;
    
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String studentName, String sUserName, String sDate, List<StudentDetails> details) {
		super();
		this.studentName = studentName;
		this.sUserName = sUserName;
		this.sDate = sDate;
		this.details = details;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getsUserName() {
		return sUserName;
	}

	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	
	public List<StudentDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StudentDetails> details) {
		this.details = details;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	
	
}
