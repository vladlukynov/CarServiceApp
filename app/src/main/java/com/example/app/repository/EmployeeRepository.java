package com.example.app.repository;

import com.example.app.entity.Employee;

import static com.example.app.utils.DatabaseAuth.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    public List<Employee> getEmployeesInfo() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetEmployeesInfo");
             ResultSet resultSet = statement.executeQuery()) {
            List<Employee> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Employee(resultSet.getString("UserLogin"),
                        resultSet.getString("Pass"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getInt("RoleId"),
                        resultSet.getBoolean("IsActive"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("MiddleName"),
                        resultSet.getString("Post"),
                        resultSet.getDouble("Salary"),
                        resultSet.getDate("Birthday").toLocalDate()));
            }

            return list;
        }
    }

    public void updateEmployee(String userLogin, Employee newEmployee) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC EditEmployee '" + userLogin + "',"
                     + "'" + newEmployee.getUserLogin() + "',"
                     + "'" + newEmployee.getPass() + "',"
                     + "'" + newEmployee.getEmail() + "',"
                     + "'" + newEmployee.getPhoneNumber() + "',"
                     + newEmployee.getRoleId() + ","
                     + "N'" + newEmployee.getFirstName() + "',"
                     + "N'" + newEmployee.getLastName() + "',"
                     + "N'" + newEmployee.getMiddleName() + "',"
                     + "N'" + newEmployee.getPost() + "',"
                     + newEmployee.getSalary() + ","
                     + "'" + newEmployee.getBirthday() + "'")) {
            statement.execute();
        }
    }

    public void registerEmployee(Employee employee) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC RegisterEmployee '" + employee.getUserLogin() + "',"
                     + "'" + employee.getPass() + "',"
                     + "'" + employee.getEmail() + "',"
                     + "'" + employee.getPhoneNumber() + "',"
                     + employee.getRoleId() + ","
                     + "N'" + employee.getFirstName() + "',"
                     + "N'" + employee.getLastName() + "',"
                     + "N'" + employee.getMiddleName() + "',"
                     + "N'" + employee.getPost() + "',"
                     + employee.getSalary() + ","
                     + "'" + employee.getBirthday() + "'")) {
            statement.execute();
        }
    }
}
