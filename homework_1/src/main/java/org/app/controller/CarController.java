package org.app.controller;

import org.app.entity.Car;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.HasAnyRole;
import org.app.ioc.annotation.Logging;

import java.util.List;
import java.util.UUID;

public interface CarController {
    Car getCarByUUID(UUID id);
    List<Car> viewCars();
    List<Car> searchCars(Car carToSearch);
    @HasAnyRole(roles = {"ADMIN", "MANAGER"})
    @Logging(MethodDescription = "добавление автомобиля")
    Car addCar(Car car, UserCredentials credentials);
    @HasAnyRole(roles = {"ADMIN", "MANAGER"})
    @Logging(MethodDescription = "удаление автомобиля")
    void deleteCar(Car car, UserCredentials credentials);
}
