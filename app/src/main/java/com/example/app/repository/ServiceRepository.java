package com.example.app.repository;

import com.example.app.entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class ServiceRepository {
    public List<Service> getServices() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetServicesInfo");
             ResultSet resultSet = statement.executeQuery()) {
            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                Service service = new Service(resultSet.getInt("ServiceId"),
                        resultSet.getString("ServiceName"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("Price"),
                        resultSet.getBoolean("IsActive"));
                services.add(service);
            }
            return services;
        }
    }

    public void changeServiceStatus(int serviceId, int status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC ChangeServiceStatus " + serviceId + "," + status)) {
            statement.execute();
        }
    }

    public void addService(Service service) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC AddService '" + service.getServiceName() + "','"
                     + service.getDescription() + "',"
                     + service.getPrice())) {
            statement.execute();
        }
    }

    public void updateService(int serviceId, Service newService) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC EditService " + serviceId + ",N'"
                     + newService.getServiceName() + "',N'"
                     + newService.getDescription() + "',"
                     + newService.getPrice())) {
            statement.execute();
        }
    }
}
