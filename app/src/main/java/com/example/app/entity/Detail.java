package com.example.app.entity;

public class Detail {
    private int detailId;
    private final String detailName;
    private final double price;
    private final int quantity;

    public Detail(String detailName, double price, int quantity) {
        this.detailName = detailName;
        this.price = price;
        this.quantity = quantity;
    }

    public Detail(int detailId, String detailName, double price, int quantity) {
        this.detailId = detailId;
        this.detailName = detailName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getDetailId() {
        return detailId;
    }

    public String getDetailName() {
        return detailName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "detailId=" + detailId +
                ", detailName='" + detailName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
