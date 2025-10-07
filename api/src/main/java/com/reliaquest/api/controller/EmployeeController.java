package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/employee") // Base path
public class EmployeeController implements IEmployeeController<Employee, Employee> {

    private final EmployeeService employeeService;

    // Get all employees
    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Search by name
    @Override
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        log.info("Searching employees by name fragment: {}", searchString);
        List<Employee> filtered = employeeService.getAllEmployees().stream()
                .filter(emp -> emp.getEmployee_name().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    // Highest salary
    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("Fetching highest salary");
        Integer maxSalary = employeeService.getAllEmployees().stream()
                .map(Employee::getEmployee_salary)
                .max(Integer::compareTo)
                .orElse(0);
        return ResponseEntity.ok(maxSalary);
    }

    // Top 10 highest earning employee names
    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Fetching top 10 highest earning employees");
        List<String> topTen = employeeService.getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getEmployee_salary).reversed())
                .limit(10)
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topTen);
    }

    // Get employee by ID (UUID-safe)
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        log.info("Fetching employee by ID: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // Create employee
    @Override
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee input) {
        log.info("Creating new employee: {}", input);
        Employee created = employeeService.createEmployee(input);
        return ResponseEntity.ok(created);
    }

    // Delete employee
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        log.info("Deleting employee by ID: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(employee.getEmployee_name());
    }
}
