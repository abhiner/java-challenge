package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeListResponse;
import com.reliaquest.api.model.EmployeeSingleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8112/api/v1/employee";

    // Get all employees
    public List<Employee> getAllEmployees() {
        EmployeeListResponse response = restTemplate.getForObject(BASE_URL, EmployeeListResponse.class);
        return response != null ? response.getData() : List.of();
    }

    // Get employee by ID
    public Employee getEmployeeById(String id) {
        EmployeeSingleResponse response = restTemplate.getForObject(BASE_URL + "/" + id, EmployeeSingleResponse.class);
        return response != null ? response.getData() : null;
    }

    // Create employee
    public Employee createEmployee(Employee employee) {
        EmployeeSingleResponse response = restTemplate.postForObject(BASE_URL, employee, EmployeeSingleResponse.class);
        return response != null ? response.getData() : null;
    }

    // Delete employee
    public void deleteEmployee(String id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
