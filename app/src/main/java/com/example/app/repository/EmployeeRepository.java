package com.example.app.repository;

import com.example.app.entity.Employee;

import static com.example.app.utils.DatabaseAuth.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    public List<Employee> getEmployeesInfo() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetEmployeesInfo");
            ResultSet resultSet = statement.executeQuery();

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
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC EditEmployee ?,?,?,?,?,?,?,?,?,?,?,?");
            statement.setString(1, userLogin);
            statement.setString(2, newEmployee.getUserLogin());
            statement.setString(3, newEmployee.getPass());
            statement.setString(4, newEmployee.getEmail());
            statement.setString(5, newEmployee.getPhoneNumber());
            statement.setInt(6, newEmployee.getRoleId());
            statement.setString(7, newEmployee.getFirstName());
            statement.setString(8, newEmployee.getLastName());
            statement.setString(9, newEmployee.getMiddleName());
            statement.setString(10, newEmployee.getPost());
            statement.setDouble(11, newEmployee.getSalary());
            statement.setString(12, newEmployee.getBirthday().toString());

            statement.execute();
        }
    }

    public void registerEmployee(Employee employee) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC RegisterEmployee ?,?,?,?,?,?,?,?,?,?,?");
            statement.setString(1, employee.getUserLogin());
            statement.setString(2, employee.getPass());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getPhoneNumber());
            statement.setInt(5, employee.getRoleId());
            statement.setString(6, employee.getFirstName());
            statement.setString(7, employee.getLastName());
            statement.setString(8, employee.getMiddleName());
            statement.setString(9, employee.getPost());
            statement.setDouble(10, employee.getSalary());
            statement.setString(11, employee.getBirthday().toString());

            statement.execute();
        }
    }
}
