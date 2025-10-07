package com.reliaquest.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        emp1 = new Employee();
        emp1.setId("1");
        emp1.setEmployee_name("Alice");
        emp1.setEmployee_salary(1000);
        emp1.setEmployee_age(30);

        emp2 = new Employee();
        emp2.setId("2");
        emp2.setEmployee_name("Bob");
        emp2.setEmployee_salary(2000);
        emp2.setEmployee_age(40);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getEmployee_name()).isEqualTo("Alice");
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById("1")).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");

        assertThat(response.getBody().getEmployee_name()).isEqualTo("Alice");
        verify(employeeService, times(1)).getEmployeeById("1");
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

        assertThat(response.getBody()).isEqualTo(2000);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

        assertThat(response.getBody()).containsExactly("Bob", "Alice"); // sorted by salary desc
    }

    @Test
    void testCreateEmployee() {
        when(employeeService.createEmployee(emp1)).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.createEmployee(emp1);

        assertThat(response.getBody().getEmployee_name()).isEqualTo("Alice");
        verify(employeeService, times(1)).createEmployee(emp1);
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeService.getEmployeeById("1")).thenReturn(emp1);
        doNothing().when(employeeService).deleteEmployee("1");

        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");

        assertThat(response.getBody()).isEqualTo("Alice");
        verify(employeeService, times(1)).deleteEmployee("1");
    }
}
