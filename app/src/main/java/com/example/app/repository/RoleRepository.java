package com.example.app.repository;

import com.example.app.entity.Role;

import java.sql.*;

import static com.example.app.utils.DatabaseAuth.*;

public class RoleRepository {
    public Role getRoleByName(String roleName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Roles WHERE RoleName=?");
            statement.setString(1, roleName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Role(resultSet.getInt("RoleId"),
                        resultSet.getString("RoleName")
                );
            }
            return null;
        }
    }

    public Role getRoleById(int roleId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Roles WHERE RoleId=?");
            statement.setInt(1, roleId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Role(resultSet.getInt("RoleId"),
                        resultSet.getString("RoleName"));
            }
            return null;
        }
    }
}
