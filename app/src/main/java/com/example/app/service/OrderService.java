package com.example.app.service;

import com.example.app.entity.Order;
import com.example.app.entity.OrderElement;
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
                orderElements.add(new OrderElement(order_.getElement(), order_.getQuantity(), order_.getElementSum()));
            }
            order.setElements(orderElements);

            result.add(order);
        }
        return result;
    }
}
