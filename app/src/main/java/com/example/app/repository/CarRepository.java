package com.example.app.repository;

import com.example.app.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class CarRepository {
    public List<Car> getCars() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetCarsInfo");
            ResultSet resultSet = statement.executeQuery();

            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("CarId"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("CarModel"),
                        resultSet.getInt("ReleaseYear"),
                        resultSet.getBoolean("IsActive")));
            }

            return cars;
        }
    }

    public void changeCarStatus(int carId, int status) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC ChangeCarStatus ?,?");
            statement.setInt(1, carId);
            statement.setInt(2, status);

            statement.execute();
        }
    }

    public void addCar(Car car) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddCar ?,?,?");
            statement.setString(1, car.getManufacturer());
            statement.setString(2, car.getCarModel());
            statement.setInt(3, car.getReleaseYear());

            statement.execute();
        }
    }

    public List<Car> getClientCars(String clientLogin) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC GetClientCars ?");
            statement.setString(1, clientLogin);
            ResultSet resultSet = statement.executeQuery();

            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("CarId"),
                        resultSet.getString("Manufacturer"),
                        resultSet.getString("CarModel"),
                        resultSet.getInt("ReleaseYear"),
                        resultSet.getString("CarNumber"),
                        resultSet.getDate("CreationDate").toLocalDate()));
            }
            return cars;
        }
    }
}
