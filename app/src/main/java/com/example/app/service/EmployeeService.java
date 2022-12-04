package com.example.app.service;

import com.example.app.entity.Employee;
import com.example.app.repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public void getEmployeesInfo() {
        try {
            List<Employee> employees;
            employees = employeeRepository.getEmployeesInfo();

            for (Employee employee : employees) {
                System.out.println(employee);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
