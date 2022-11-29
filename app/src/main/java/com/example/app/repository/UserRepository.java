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
                     SELECT UserLogin, Pass FROM GetEmployeesInfo
                     UNION
                     SELECT UserLogin, Pass FROM GetClientsInfo
                     """)) {
            List<User> list = new ArrayList<>();

            while (resultSet.next()) {
                String userLogin = resultSet.getString("UserLogin");
                String pass = resultSet.getString("Pass");
                User user = new User(userLogin, pass);
                list.add(user);
            }

            return list;
        }
    }
}
