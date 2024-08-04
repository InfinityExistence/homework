package org.app.service;

import org.app.entity.Car;
import org.app.ioc.annotation.AutoInjected;
import org.app.repository.CarRepository;

import java.util.List;
import java.util.UUID;

public class CarServiceImpl implements CarService {
    @AutoInjected
    CarRepository carRepository;
    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> findCar(Car car) {
        return carRepository.find(car);
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public Car getCarByUUID(UUID id) {
        return carRepository.findCarById(id);
    }
}
