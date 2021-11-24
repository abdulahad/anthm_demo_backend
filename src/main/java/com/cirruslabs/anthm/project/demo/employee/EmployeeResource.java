package com.cirruslabs.anthm.project.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
public class EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Get All Employee

    //Create Employee

    //Search Employee
}
