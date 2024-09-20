package com.practice.p4.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.p4.Entity.Department;
import com.practice.p4.repository.DepartmentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartmentService {
	
	@Autowired
    private DepartmentRepo departmentRepository;

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get department by ID
    public Department getDepartmentById(int deptId) {
        return departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + deptId));
    }

    // Add a new department
    public Department addDepartment(Department department) {
        // Ensure that the department does not have an ID yet (new department)
        if (department.getDeptId() != 0) {
            throw new IllegalArgumentException("New department cannot already have an ID");
        }
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(int id, Department updatedDepartment) {
        // Check if the department exists
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with ID " + id + " not found"));

        // Update the fields of the existing department
        existingDepartment.setDeptName(updatedDepartment.getDeptName());
        existingDepartment.setDeptSpecification(updatedDepartment.getDeptSpecification());

        // Save and return the updated department
        return departmentRepository.save(existingDepartment);
    }

    // Delete department by ID
    public void deleteDepartment(int deptId) {
        if (!departmentRepository.existsById(deptId)) {
            throw new RuntimeException("Department not found with ID: " + deptId);
        }
        departmentRepository.deleteById(deptId);
    
}
}
