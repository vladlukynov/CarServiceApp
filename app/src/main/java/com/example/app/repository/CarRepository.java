package com.example.app.repository;

import com.example.app.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class CarRepository {
    public List<Car> getCars() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GetCarsInfo")) {
            List<Car> cars = new ArrayList<>();

            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getInt("CarId"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("CarModel"),
                        resultSet.getInt("ReleaseYear"),
                        resultSet.getBoolean("IsActive")
                );

                cars.add(car);
            }

            return cars;
        }
    }

    public void activateCar(int carId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC ChangeCarStatus " + carId + "," + 1);
        }
    }

    public void deactivateCar(int carId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC ChangeCarStatus " + carId + "," + 0);
        }
    }

    public void updateCar(Car newCar, int carId) throws SQLException {
        addCar(newCar);
        deactivateCar(carId);
    }

    public void addCar(Car car) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC AddCar '" + car.getManufacturer() + "','" +
                    car.getCarModel() + "'," +
                    car.getReleaseYear());
        }
    }
}
