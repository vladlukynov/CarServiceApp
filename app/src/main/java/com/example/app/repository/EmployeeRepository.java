package com.example.app.repository;

import com.example.app.entity.Employee;

import static com.example.app.utils.DatabaseAuth.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeRepository {
    public List<Employee> getEmployeesInfo() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GetEmployeesInfo")) {
            List<Employee> list = new ArrayList<>();
            while (resultSet.next()) {
                String userLogin = resultSet.getString("UserLogin");
                String pass = resultSet.getString("Pass");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int roleId = resultSet.getInt("RoleId");
                boolean isActive = resultSet.getBoolean("IsActive");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String middleName = resultSet.getString("MiddleName");
                String post = resultSet.getString("Post");
                double salary = resultSet.getDouble("Salary");
                Date birthday = resultSet.getDate("Birthday");

                Employee employee = new Employee(userLogin, pass, email, phoneNumber, roleId, isActive,
                        firstName, lastName, middleName, post, salary, birthday);

                list.add(employee);
            }

            return list;
        }
    }
}
