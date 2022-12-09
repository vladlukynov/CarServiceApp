package com.example.app.repository;

import com.example.app.entity.User;

import static com.example.app.utils.DatabaseAuth.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> getUsers() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT UserLogin, Pass, Email, PhoneNumber, RoleId, IsActive FROM GetEmployeesInfo
                     UNION
                     SELECT UserLogin, Pass, Email, PhoneNumber, RoleId, IsActive FROM GetClientsInfo
                     """);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> list = new ArrayList<>();

            while (resultSet.next()) {
                list.add(new User(resultSet.getString("UserLogin"),
                        resultSet.getString("Pass"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getInt("RoleId"),
                        resultSet.getBoolean("IsActive")));
            }

            return list;
        }
    }

    public void changeUserStatus(String userLogin, int status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC ChangeUserStatus '" + userLogin + "'," + status)) {
            statement.execute();
        }
    }
}
