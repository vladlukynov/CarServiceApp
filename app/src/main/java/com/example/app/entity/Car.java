package com.example.app.entity;

import java.time.LocalDate;

public class Car {
    private int carId;
    private final String manufacturer;
    private final String carModel;
    private final int releaseYear;
    private boolean isActive;
    private String carNumber;
    private LocalDate creationDate;

    public Car(int carId, String manufacturer, String carModel, int releaseYear, String carNumber, LocalDate creationDate) {
        this.carId = carId;
        this.manufacturer = manufacturer;
        this.carModel = carModel;
        this.releaseYear = releaseYear;
        this.carNumber = carNumber;
        this.creationDate = creationDate;
    }

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

    public String getCarNumber() {
        return carNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", manufacturer='" + manufacturer + '\'' +
                ", carModel='" + carModel + '\'' +
                ", releaseYear=" + releaseYear +
                ", isActive=" + isActive +
                ", carNumber='" + carNumber + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
