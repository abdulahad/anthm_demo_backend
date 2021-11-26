package com.cirruslabs.anthm.project.demo.employee;

import com.cirruslabs.anthm.project.demo.employee.model.Employee;
import com.cirruslabs.anthm.project.demo.employee.model.EmployeePage;
import com.cirruslabs.anthm.project.demo.employee.model.EmployeeSearchCriteria;
import com.cirruslabs.anthm.project.demo.employee.repository.EmployeeRepository;
import com.cirruslabs.anthm.project.demo.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

//    //Get All Employee
//    @GetMapping
//    public List<Employee> getAll() {
//        return employeeRepository.findAll();
//    }

    //Get Employee By ID
    @GetMapping("/{id}")
    public Employee get(@PathVariable Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return null;
        }
        return employee.get();
    }

    //Create Employee
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> create(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
                path("/{id}").buildAndExpand(savedEmployee.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    //Search Employee
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Page<Employee>> search(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria) {

        return new ResponseEntity<>(employeeService.getEmployees(employeePage, employeeSearchCriteria), HttpStatus.OK);
    }
}
