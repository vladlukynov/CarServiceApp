package com.example.app.repository;

import com.example.app.entity.User;

import static com.example.app.utils.DatabaseAuth.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> getUsers() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                     SELECT UserLogin, Pass, Email, PhoneNumber, RoleId, IsActive FROM GetEmployeesInfo
                     UNION
                     SELECT UserLogin, Pass, Email, PhoneNumber, RoleId, IsActive FROM GetClientsInfo
                     """)) {
            List<User> list = new ArrayList<>();

            while (resultSet.next()) {
                String userLogin = resultSet.getString("UserLogin");
                String pass = resultSet.getString("Pass");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int roleId = resultSet.getInt("RoleId");
                boolean isActive = resultSet.getBoolean("IsActive");
                User user = new User(userLogin, pass, email, phoneNumber, roleId, isActive);

                list.add(user);
            }

            return list;
        }
    }
}
