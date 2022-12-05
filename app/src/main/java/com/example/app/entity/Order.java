package com.example.app.entity;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private final int orderId;
    private final int carId;
    private final String carNumber;
    private final String status;
    private final LocalDate creationDate;
    private final LocalDate statusChangeDate;
    private final String employeeLogin;
    private final String clientLogin;
    private List<OrderElement> elements;
    private String element;
    private int quantity;
    private double elementSum;
    private final double total;

    public Order(int orderId, int carId, String carNumber, String status, LocalDate creationDate, LocalDate statusChangeDate, String employeeLogin, String clientLogin, String element, int quantity, double elementSum, double total) {
        this.orderId = orderId;
        this.carId = carId;
        this.carNumber = carNumber;
        this.status = status;
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.employeeLogin = employeeLogin;
        this.clientLogin = clientLogin;
        this.element = element;
        this.quantity = quantity;
        this.elementSum = elementSum;
        this.total = total;
    }

    public Order(int orderId, int carId, String carNumber, String status, LocalDate creationDate, LocalDate statusChangeDate, String employeeLogin, String clientLogin, double total) {
        this.orderId = orderId;
        this.carId = carId;
        this.carNumber = carNumber;
        this.status = status;
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.employeeLogin = employeeLogin;
        this.clientLogin = clientLogin;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getStatusChangeDate() {
        return statusChangeDate;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public List<OrderElement> getElements() {
        return elements;
    }

    public String getElement() {
        return element;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getElementSum() {
        return elementSum;
    }

    public double getTotal() {
        return total;
    }

    public void setElements(List<OrderElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", carId=" + carId +
                ", carNumber='" + carNumber + '\'' +
                ", stattus='" + status + '\'' +
                ", creationDate=" + creationDate +
                ", statusChangeDate=" + statusChangeDate +
                ", employeeLogin='" + employeeLogin + '\'' +
                ", ClientLogin='" + clientLogin + '\'' +
                '}';
    }
}
