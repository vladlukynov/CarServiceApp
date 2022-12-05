package com.example.app.entity;

public class Car {
    private int carId;
    private final String manufacturer;
    private final String carModel;
    private final int releaseYear;
    private final boolean isActive;

    public Car(String manufacturer, String carModel, int releaseYear, boolean isActive) {
        this.manufacturer = manufacturer;
        this.carModel = carModel;
        this.releaseYear = releaseYear;
        this.isActive = isActive;
    }

    public Car(int carId, String manufacturer, String carModel, int releaseYear, boolean isActive) {
        this.carId = carId;
        this.manufacturer = manufacturer;
        this.carModel = carModel;
        this.releaseYear = releaseYear;
        this.isActive = isActive;
    }

    public int getCarId() {
        return carId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", manufacturer='" + manufacturer + '\'' +
                ", carModel='" + carModel + '\'' +
                ", releaseYear=" + releaseYear +
                ", isActive=" + isActive +
                '}';
    }
}
