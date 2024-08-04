package org.app.service;

import org.app.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {
    Car saveCar(Car car);
    List<Car> findCar(Car car);
    List<Car> findAllCars();
    void deleteCar(Car car);

    Car getCarByUUID(UUID id);
}
