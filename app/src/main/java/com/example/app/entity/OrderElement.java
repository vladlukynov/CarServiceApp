package com.example.app.entity;

public class OrderElement {
    private final String element;
    private final int quantity;
    private final double elementSum;

    public OrderElement(String element, int quantity, double elementSum) {
        this.element = element;
        this.quantity = quantity;
        this.elementSum = elementSum;
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
}
