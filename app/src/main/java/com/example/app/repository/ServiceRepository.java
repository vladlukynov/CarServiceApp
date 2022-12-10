package com.example.app.repository;

import com.example.app.entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class ServiceRepository {
    public List<Service> getServices() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetServicesInfo");
            ResultSet resultSet = statement.executeQuery();

            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                services.add(new Service(resultSet.getInt("ServiceId"),
                        resultSet.getString("ServiceName"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("Price"),
                        resultSet.getBoolean("IsActive")));
            }
            return services;
        }
    }

    public void changeServiceStatus(int serviceId, int status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC ChangeServiceStatus ?,?");
            statement.setInt(1, serviceId);
            statement.setInt(2, status);

            statement.execute();
        }
    }

    public void addService(Service service) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddService ?,?,?");
            statement.setString(1, service.getServiceName());
            statement.setString(2, service.getDescription());
            statement.setDouble(3, service.getPrice());

            statement.execute();
        }
    }

    public void updateService(int serviceId, Service newService) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC EditService ?,?,?,?");
            statement.setInt(1, serviceId);
            statement.setString(2, newService.getServiceName());
            statement.setString(3, newService.getDescription());
            statement.setDouble(4, newService.getPrice());

            statement.execute();
        }
    }
}
