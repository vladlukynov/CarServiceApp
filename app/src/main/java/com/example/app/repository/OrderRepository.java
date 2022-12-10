package com.example.app.repository;

import com.example.app.entity.Detail;
import com.example.app.entity.Order;
import com.example.app.entity.Service;
import com.example.app.exception.NoDetailByIdException;
import com.example.app.exception.NoServiceByIdException;
import com.example.app.service.DetailService;
import com.example.app.service.ServiceService;

import java.sql.*;
import java.util.*;

import static com.example.app.utils.DatabaseAuth.*;

public class OrderRepository {
    private final DetailService detailService = new DetailService();
    private final ServiceService serviceService = new ServiceService();

    public List<Order> getOrders() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetOrdersWithSum");

            return processOrderLists(statement.executeQuery());
        }
    }

    public void addOrder(int carId, String carNumber, String clientLogin) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddOrder ?,?,?");
            statement.setInt(1, carId);
            statement.setString(2, carNumber);
            statement.setString(3, clientLogin);

            statement.execute();
        }
    }

    public List<Order> getClientOrders(String clientLogin) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC GetClientOrdersWithSum ?");
            statement.setString(1, clientLogin);

            return processOrderLists(statement.executeQuery());
        }
    }

    public void changeOrderStatus(int orderId, String status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC ChangeOrderStatus ?,?");
            statement.setInt(1, orderId);
            statement.setString(2, status);

            statement.execute();
        }
    }

    public void addEmployeeToOrder(int orderId, String employeeLogin, String status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddEmployeeToOrder ?,?,?");
            statement.setInt(1, orderId);
            statement.setString(2, employeeLogin);
            statement.setString(3, status);

            statement.execute();
        }
    }

    public List<Detail> getOrderDetails(int orderId) throws SQLException, NoDetailByIdException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersDetails WHERE OrderId=?");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            List<Detail> details = new ArrayList<>();
            while (resultSet.next()) {
                details.add(detailService.getDetail(resultSet.getInt("DetailId")));
            }
            return details;
        }
    }

    public List<Service> getOrdersServices(int orderId) throws SQLException, NoServiceByIdException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersServices WHERE OrderId=?");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                services.add(serviceService.getService(resultSet.getInt("ServiceId")));
            }
            return services;
        }
    }

    public int getDetailQuantityInOrder(int orderId, int detailId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersDetails WHERE DetailId=? AND OrderId=?");
            statement.setInt(1, detailId);
            statement.setInt(2, orderId);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() ? resultSet.getInt("Quantity") : 0;
        }
    }

    public void addDetailToOrder(int orderId, int detailId, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddDetailToOrder2 ?,?,?");
            statement.setInt(1, orderId);
            statement.setInt(2, detailId);
            statement.setInt(3, quantity);

            statement.execute();
        }
    }

    public void deleteDetailFromOrder(int orderId, int detailId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC DeleteDetailFromOrder2 ?,?");
            statement.setInt(1, orderId);
            statement.setInt(2, detailId);

            statement.execute();
        }
    }

    public void addServiceToOrder(int serviceId, int orderId, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddServiceToOrder ?,?,?");
            statement.setInt(1, serviceId);
            statement.setInt(2, orderId);
            statement.setInt(3, quantity);

            statement.execute();
        }
    }

    public void deleteServiceFromOrder(int serviceId, int orderId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC DeleteServiceFromOrder ?,?");
            statement.setInt(1, serviceId);
            statement.setInt(2, orderId);

            statement.execute();
        }
    }

    public int getServiceQuantityInOrder(int orderId, int serviceId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersServices WHERE ServiceId=? AND OrderId=?");
            statement.setInt(1, serviceId);
            statement.setInt(2, orderId);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() ? resultSet.getInt("Quantity") : 0;
        }
    }

    private static List<Order> processOrderLists(ResultSet resultSet) throws SQLException {
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
