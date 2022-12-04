package com.example.app.service;

import com.example.app.entity.Employee;
import com.example.app.exception.NoEmployeeByLoginException;
import com.example.app.repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public List<Employee> getEmployeesInfo() throws SQLException {
        return employeeRepository.getEmployeesInfo();
    }

    public Employee getEmployeeInfo(String userLogin) throws SQLException, NoEmployeeByLoginException {
        List<Employee> employees = employeeRepository.getEmployeesInfo();

        Optional<Employee> employee = employees.stream().filter(user_ -> user_.getUserLogin().equals(userLogin)).findFirst();
        if (employee.isPresent()) {
            return employee.get();
        }

        throw new NoEmployeeByLoginException("No employee with " + userLogin + " login");
    }

    public void updateEmployee(String userLogin, Employee newEmployee) throws SQLException {
        employeeRepository.updateEmployee(userLogin, newEmployee);
    }

    public void registerEmployee(Employee employee) throws SQLException {
        employeeRepository.registerEmployee(employee);
    }
}
