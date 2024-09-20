package com.practice.p4.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StudentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentDetailsId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "student_id")
	private Student student;
	
	private int courseId;
	private int deptId;
	private String sAddress;
	private String mobileNo;
	
	
	public StudentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	public StudentDetails(int id, Student student, int courseId, int deptId, String sAddress, String mobileNo) {
		super();
		this.studentDetailsId = id;
		this.student = student;
		this.courseId = courseId;
		this.deptId = deptId;
		this.sAddress = sAddress;
		this.mobileNo = mobileNo;
	}


	public int getStudentDetailsId() {
		return studentDetailsId;
	}


	public void setStudentDetailsId(int studentDetailsId) {
		this.studentDetailsId = studentDetailsId;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public int getCourseId() {
		return courseId;
	}


	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	public int getDeptId() {
		return deptId;
	}


	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}


	public String getsAddress() {
		return sAddress;
	}


	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
	
	
}
