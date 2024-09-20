package com.practice.p4.pojo;

import java.util.List;

import com.practice.p4.Entity.StudentDetails;

public class StudentRequestBody {
	
	private String studentName;
	private String sUserName;
	private String sDate;
	private String address;
	private String mobileNo;
	private String courseName;
	private String courseDuration;
	private String deptName;
	private String deptDescription;
	
	private List<StudentDetails> details;

	public StudentRequestBody(String studentName, String sUserName,String sDate, String address, String mobileNo, String courseName,
			String courseDuration, String dept, String deptDescription, List<StudentDetails> details) {
		super();
		this.studentName = studentName;
		this.sUserName = sUserName;
		this.sDate = sDate;
		this.address = address;
		this.mobileNo = mobileNo;
		this.courseName = courseName;
		this.courseDuration = courseDuration;
		this.deptName = dept;
		this.deptDescription = deptDescription;
		this.details = details;
	}

	public StudentRequestBody() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String dept) {
		this.deptName = dept;
	}

	public String getDeptDescription() {
		return deptDescription;
	}

	public void setDeptDescription(String deptDescription) {
		this.deptDescription = deptDescription;
	}

	public List<StudentDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StudentDetails> details) {
		this.details = details;
	} 
	
	
	
}
