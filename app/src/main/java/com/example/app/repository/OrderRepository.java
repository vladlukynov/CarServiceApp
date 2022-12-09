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
             PreparedStatement statement = connection.prepareStatement("EXEC GetClientOrdersWithSum '" + clientLogin + "'");
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

    public void changeOrderStatus(int orderId, String status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC ChangeOrderStatus " + orderId + ","
                     + "'" + status + "'")) {
            statement.execute();
        }
    }

    public void addEmployeeToOrder(int orderId, String employeeLogin, String status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC AddEmployeeToOrder " + orderId + ","
                     + "'" + employeeLogin + "',"
                     + "N'" + status + "'")) {
            statement.execute();
        }
    }

    public List<Detail> getOrderDetails(int orderId) throws SQLException, NoDetailByIdException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersDetails WHERE OrderId=" + orderId);
             ResultSet resultSet = statement.executeQuery()) {
            List<Detail> details = new ArrayList<>();
            while (resultSet.next()) {
                details.add(detailService.getDetail(resultSet.getInt("DetailId")));
            }
            return details;
        }
    }

    public List<Service> getOrdersServices(int orderId) throws SQLException, NoServiceByIdException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersServices WHERE OrderId=" + orderId);
             ResultSet resultSet = statement.executeQuery()) {
            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                services.add(serviceService.getService(resultSet.getInt("ServiceId")));
            }
            return services;
        }
    }

    public int getDetailQuantityInOrder(int orderId, int detailId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersDetails WHERE DetailId=" + detailId + " AND "
                     + "OrderId=" + orderId);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("Quantity");
            }

            return 0;
        }
    }

    public void addDetailToOrder(int orderId, int detailId, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC AddDetailToOrder2 " + orderId + ","
                     + detailId + ","
                     + quantity)) {
            statement.execute();
        }
    }

    public void deleteDetailFromOrder(int orderId, int detailId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC DeleteDetailFromOrder2 " + orderId + ","
                     + detailId)) {
            statement.execute();
        }
    }

    public void addServiceToOrder(int serviceId, int orderId, int quantity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC AddServiceToOrder " + serviceId + ","
                     + orderId + ","
                     + quantity)) {
            statement.execute();
        }
    }

    public void deleteServiceFromOrder(int serviceId, int orderId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("EXEC DeleteServiceFromOrder " + serviceId + ","
                     + orderId)) {
            statement.execute();
        }
    }

    public int getServiceQuantityInOrder(int orderId, int serviceId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrdersServices WHERE ServiceId=" + serviceId + " AND "
                     + "OrderId=" + orderId);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("Quantity");
            }

            return 0;
        }
    }
}
