package co.in.techLakhan.Employee_Cache_demo.controller;

import co.in.techLakhan.Employee_Cache_demo.entity.Employee;
import co.in.techLakhan.Employee_Cache_demo.exception.EmployeeNotFoundException;
import co.in.techLakhan.Employee_Cache_demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static co.in.techLakhan.Employee_Cache_demo.constants.ErrorCodes.EXP001;

@RestController
@RequestMapping
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping(value = "/employee/create")
    public ResponseEntity<String> newEmployee(@RequestBody Employee employee) {
        try {
            return new ResponseEntity<>(employeeService.createNewEmployeeRecord(employee), HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            throw new RuntimeException();
        }
    }


    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<Employee> employeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        try {
            return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException(404, EXP001.message(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/employee")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) throws EmployeeNotFoundException {
        try {
            employeeService.updateEmployeeDetails(employee);
            return new ResponseEntity<>("Employee details updated successfully.", HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException(404, EXP001.message(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating employee record");
        }
    }
}
