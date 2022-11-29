package com.example.app.repository;

import com.example.app.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class ClientRepository {
    public List<Client> getClientsInfo() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GetClientsInfo")) {
            List<Client> list = new ArrayList<>();
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
                Date birthday = resultSet.getDate("Birthday");

                Client client = new Client(userLogin, pass, email, phoneNumber, roleId, isActive,
                        firstName, lastName, middleName, birthday);

                list.add(client);
            }

            return list;
        }
    }
}
