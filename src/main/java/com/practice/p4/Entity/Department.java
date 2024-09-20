package com.practice.p4.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deptId;
	private String deptName;
	private String deptSpecification;
	
	
	public Department(String deptName, String deptSpecification) {
		super();
		this.deptName = deptName;
		this.deptSpecification = deptSpecification;
	}


	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getDeptId() {
		return deptId;
	}


	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDeptSpecification() {
		return deptSpecification;
	}


	public void setDeptSpecification(String deptSpecification) {
		this.deptSpecification = deptSpecification;
	}
	
	
	
	
	
}
