package com.practice.p4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.p4.Entity.StudentDetails;

@Repository
public interface StudentDetailsRepo extends JpaRepository<StudentDetails, Integer> {

}
