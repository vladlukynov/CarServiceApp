package com.example.app.service;

import com.example.app.entity.Detail;
import com.example.app.entity.Order;
import com.example.app.entity.OrderElement;
import com.example.app.entity.Service;
import com.example.app.exception.NoDetailByIdException;
import com.example.app.exception.NoServiceByIdException;
import com.example.app.repository.OrderRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();

    public List<Order> getOrders() throws SQLException {
        return groupOrders(orderRepository.getOrders().stream().collect(Collectors.groupingBy(Order::getOrderId)));
    }

    public void addOrder(int carId, String carNumber, String clientLogin) throws SQLException {
        orderRepository.addOrder(carId, carNumber, clientLogin);
    }

    public List<Order> getClientOrders(String clientLogin) throws SQLException {
        return groupOrders(orderRepository.getClientOrders(clientLogin).stream().collect(Collectors.groupingBy(Order::getOrderId)));
    }

    public void changeOrderStatus(int orderId, String status) throws SQLException {
        orderRepository.changeOrderStatus(orderId, status);
    }

    public void addEmployeeToOrder(int orderId, String employeeLogin, String status) throws SQLException {
        orderRepository.addEmployeeToOrder(orderId, employeeLogin, status);
    }

    private List<Order> groupOrders(Map<Integer, List<Order>> orders) {
        List<Order> result = new ArrayList<>();

        for (Integer key : orders.keySet()) {
            List<Order> orders_ = orders.get(key);

            Order order = new Order(orders_.get(0).getOrderId(),
                    orders_.get(0).getCarId(),
                    orders_.get(0).getCarNumber(),
                    orders_.get(0).getStatus(),
                    orders_.get(0).getCreationDate(),
                    orders_.get(0).getStatusChangeDate(),
                    orders_.get(0).getEmployeeLogin(),
                    orders_.get(0).getClientLogin(),
                    orders_.get(0).getTotal());

            List<OrderElement> orderElements = new ArrayList<>();
            for (Order order_ : orders_) {
                if (order_.getElement() != null) {
                    orderElements.add(new OrderElement(order_.getElement(), order_.getQuantity(), order_.getElementSum()));
                }
            }
            order.setElements(orderElements);

            result.add(order);
        }
        return result;
    }

    public List<Detail> getOrderDetails(int orderId) throws SQLException, NoDetailByIdException {
        return orderRepository.getOrderDetails(orderId);
    }

    public int getDetailQuantityInOrder(int orderId, int detailId) throws SQLException {
        return orderRepository.getDetailQuantityInOrder(orderId, detailId);
    }

    public void addDetailToOrder(int orderId, int detailId, int quantity) throws SQLException {
        orderRepository.addDetailToOrder(orderId, detailId, quantity);
    }

    public void deleteDetailFromOrder(int orderId, int detailId) throws SQLException {
        orderRepository.deleteDetailFromOrder(orderId, detailId);
    }

    public void addServiceToOrder(int serviceId, int orderId, int quantity) throws SQLException {
        orderRepository.addServiceToOrder(serviceId, orderId, quantity);
    }

    public void deleteServiceFromOrder(int serviceId, int orderId) throws SQLException {
        orderRepository.deleteServiceFromOrder(serviceId, orderId);
    }

    public int getServiceQuantityInOrder(int orderId, int serviceId) throws SQLException {
        return orderRepository.getServiceQuantityInOrder(orderId, serviceId);
    }

    public List<Service> getOrdersServices(int orderId) throws SQLException, NoServiceByIdException {
        return orderRepository.getOrdersServices(orderId);
    }
}
