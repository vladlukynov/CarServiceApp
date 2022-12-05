package com.example.app.repository;

import com.example.app.entity.Service;
import com.example.app.service.ServiceService;
import javafx.scene.effect.DropShadow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class ServiceRepository {
    public List<Service> getServices() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GetServicesInfo")) {
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

    public void activateService(int serviceId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC ChangeServiceStatus " + serviceId + "," + 1);
        }
    }

    public void deactivateService(int serviceId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC ChangeServiceStatus " + serviceId + "," + 0);
        }
    }

    public void addService(Service service) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC AddService '" + service.getServiceName() + "','" +
                    service.getDescription() + "'," +
                    service.getPrice());
        }
    }
}
