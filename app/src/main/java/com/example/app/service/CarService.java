package com.example.app.service;

import com.example.app.entity.Car;
import com.example.app.exception.NoCarByIdException;
import com.example.app.repository.CarRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarService {
    private final CarRepository carRepository = new CarRepository();

    public List<Car> getCars() throws SQLException {
        return carRepository.getCars();
    }

    public Car getCar(int carId) throws SQLException, NoCarByIdException {
        List<Car> cars = getCars();
        Optional<Car> carOptional = cars.stream().filter(car -> car.getCarId() == carId).findFirst();

        if (carOptional.isPresent()) {
            return carOptional.get();
        }

        throw new NoCarByIdException("Not found car by id " + carId);
    }
    public void activateCar(int carId) throws SQLException {
        carRepository.activateCar(carId);
    }

    public void deactivateCar(int carId) throws SQLException {
        carRepository.deactivateCar(carId);
    }

    public void updateCar(Car newCar, int carId) throws SQLException {
        carRepository.updateCar(newCar, carId);
    }

    public void addCar(Car car) throws SQLException {
        carRepository.addCar(car);
    }
}
