package org.app.controller;

import org.app.entity.Car;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.AutoInjected;
import org.app.service.CarService;

import java.util.List;
import java.util.UUID;

public class CarControllerImpl implements  CarController{
    @AutoInjected
    CarService carService;

    @Override
    public Car getCarByUUID(UUID id) {
        return carService.getCarByUUID(id);
    }

    @Override
    public List<Car> viewCars() {
        return carService.findAllCars();
    }
    @Override
    public List<Car> searchCars(Car carToSearch) {
        return carService.findCar(carToSearch);
    }
    @Override
    public Car addCar(Car car, UserCredentials credentials) {
        return carService.saveCar(car);
    }
    @Override
    public void deleteCar(Car car, UserCredentials credentials) {
        carService.deleteCar(car);
    }




}
