package com.example.app.service;

import com.example.app.entity.Car;
import com.example.app.exception.NoCarByIdException;
import com.example.app.repository.CarRepository;

import java.sql.SQLException;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

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
        carRepository.changeCarStatus(carId, 1);
    }

    public void deactivateCar(int carId) throws SQLException {
        carRepository.changeCarStatus(carId, 0);
    }

    public void updateCar(Car newCar, int carId) throws SQLException {
        carRepository.addCar(newCar);
        carRepository.changeCarStatus(carId, 0);
    }

    public void addCar(Car car) throws SQLException {
        carRepository.addCar(car);
    }

    public List<Car> getClientCars(String clientLogin) throws SQLException {
        Map<String, List<Car>> carsMap = carRepository.getClientCars(clientLogin).stream().collect(Collectors.groupingBy(Car::getCarNumber));
        List<Car> cars = new ArrayList<>();

        for (String key : carsMap.keySet()) {
            cars.add(carsMap.get(key).stream().min((o1, o2) -> {
                int duration = Period.between(o1.getCreationDate(), o2.getCreationDate()).getDays();
                return Integer.compare(0, duration);
            }).orElse(null));
        }

        return cars;
    }
}
