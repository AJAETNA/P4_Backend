package com.practice.p4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.p4.Entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
	
    Optional<Department> findByDeptName(String DeptName);

}
