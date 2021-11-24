package com.cirruslabs.anthm.project.demo.employee.service;

import com.cirruslabs.anthm.project.demo.employee.model.Employee;
import com.cirruslabs.anthm.project.demo.employee.model.EmployeePage;
import com.cirruslabs.anthm.project.demo.employee.model.EmployeeSearchCriteria;
import com.cirruslabs.anthm.project.demo.employee.repository.EmployeeCriteriaRepository;
import com.cirruslabs.anthm.project.demo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCriteriaRepository employeeCriteriaRepository;

    public Page<Employee> getEmployees(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria) {
        return employeeCriteriaRepository.findAllWithFilter(employeePage, employeeSearchCriteria);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
