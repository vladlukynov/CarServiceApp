package com.example.app.repository;

import com.example.app.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class ClientRepository {
    public List<Client> getClientsInfo() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetClientsInfo");
            ResultSet resultSet = statement.executeQuery();

            List<Client> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Client(resultSet.getString("UserLogin"),
                        resultSet.getString("Pass"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getInt("RoleId"),
                        resultSet.getBoolean("IsActive"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("MiddleName"),
                        resultSet.getDate("Birthday").toLocalDate()));
            }

            return list;
        }
    }

    public void registerClient(Client client) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC RegisterClient ?,?,?,?,?,?,?,?");
            statement.setString(1, client.getUserLogin());
            statement.setString(2, client.getPass());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPhoneNumber());
            statement.setString(5, client.getFirstName());
            statement.setString(6, client.getLastName());
            statement.setString(7, client.getMiddleName());
            statement.setString(8, client.getBirthday().toString());

            statement.execute();
        }
    }

    public void editClient(String userLogin, Client client) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC EditClient ?,?,?,?,?,?,?,?,?");
            statement.setString(1, userLogin);
            statement.setString(2, client.getUserLogin());
            statement.setString(3, client.getPass());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getPhoneNumber());
            statement.setString(6, client.getFirstName());
            statement.setString(7, client.getLastName());
            statement.setString(8, client.getMiddleName());
            statement.setString(9, client.getBirthday().toString());

            statement.execute();
        }
    }
}
