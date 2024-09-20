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

import com.practice.p4.Entity.Department;
import com.practice.p4.Service.DepartmentService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {
	
	 @Autowired
	    private DepartmentService departmentService;

	    // Get all departments
	    @GetMapping("/alldepartments")
	    public List<Department> getAllDepartments() {
	        return departmentService.getAllDepartments();
	    }

	    // Get department by ID
	    @GetMapping("getdepartmentByid/{deptId}")
	    public Department getDepartmentById(@PathVariable int deptId) {
	        return departmentService.getDepartmentById(deptId);
	    }

	    @PostMapping("/adddept")
	    public Department addDepartment(@RequestBody Department department) {
	        return departmentService.addDepartment(department);
	    }
	    
	    @PutMapping("/updatedept/{id}")
	    public Department updateDepartment(@PathVariable int id, @RequestBody Department department) {
	        return departmentService.updateDepartment(id, department);
	    }

	    // Delete a department by ID
	    @DeleteMapping("deletedept/{deptId}")
	    public void deleteDepartment(@PathVariable int deptId) {
	        departmentService.deleteDepartment(deptId);
	    }
}
