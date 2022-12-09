package com.example.app.repository;

import com.example.app.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class ClientRepository {
    public List<Client> getClientsInfo() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetClientsInfo");
             ResultSet resultSet = statement.executeQuery()) {
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
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC RegisterClient '" + client.getUserLogin() + "',"
                     + "'" + client.getPass() + "',"
                     + "'" + client.getEmail() + "',"
                     + "'" + client.getPhoneNumber() + "',"
                     + "N'" + client.getFirstName() + "',"
                     + "N'" + client.getLastName() + "',"
                     + "N'" + client.getMiddleName() + "',"
                     + "'" + client.getBirthday() + "'")) {
            statement.execute();
        }
    }

    public void editClient(String userLogin, Client client) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC EditClient '" + userLogin + "',"
                     + "'" + client.getUserLogin() + "',"
                     + "'" + client.getPass() + "',"
                     + "'" + client.getEmail() + "',"
                     + "'" + client.getPhoneNumber() + "',"
                     + "N'" + client.getFirstName() + "',"
                     + "N'" + client.getLastName() + "',"
                     + "N'" + client.getMiddleName() + "',"
                     + "'" + client.getBirthday() + "'")) {
            statement.execute();
        }
    }
}
