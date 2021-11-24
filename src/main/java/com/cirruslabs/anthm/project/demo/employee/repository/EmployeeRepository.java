package com.cirruslabs.anthm.project.demo.employee.repository;

import com.cirruslabs.anthm.project.demo.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
