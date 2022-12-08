package com.example.app.repository;

import com.example.app.entity.Order;

import java.sql.*;
import java.util.*;

import static com.example.app.utils.DatabaseAuth.*;

public class OrderRepository {
    public List<Order> getOrders() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GetOrdersWithSum")) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("OrderId"),
                        resultSet.getInt("CarId"),
                        resultSet.getString("CarNumber"),
                        resultSet.getString("Status"),
                        resultSet.getDate("CreationDate").toLocalDate(),
                        resultSet.getDate("StatusChangeDate").toLocalDate(),
                        resultSet.getString("EmployeeLogin"),
                        resultSet.getString("ClientLogin"),
                        resultSet.getString("Element"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("ElementSum"),
                        resultSet.getDouble("Total"));

                orders.add(order);
            }
            return orders;
        }
    }

    public void addOrder(int carId, String carNumber, String clientLogin) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC AddOrder " + carId + ","
                     + "'" + carNumber + "',"
                     + "'" + clientLogin + "'")) {
            statement.execute();
        }
    }

    public List<Order> getClientOrders(String clientLogin) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC GetClientOrdersWithSum N'" + clientLogin + "'");
             ResultSet resultSet = statement.executeQuery()) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getInt("OrderId"),
                        resultSet.getInt("CarId"),
                        resultSet.getString("CarNumber"),
                        resultSet.getString("Status"),
                        resultSet.getDate("CreationDate").toLocalDate(),
                        resultSet.getDate("StatusChangeDate").toLocalDate(),
                        resultSet.getString("EmployeeLogin"),
                        resultSet.getString("ClientLogin"),
                        resultSet.getString("Element"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("ElementSum"),
                        resultSet.getDouble("Total")));
            }
            return orders;
        }
    }
}
