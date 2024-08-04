package org.app.repository;

import org.app.entity.Car;

import java.util.List;
import java.util.UUID;

public interface CarRepository {
    Car save(Car car);
    List<Car> find(Car car);
    List<Car> findAll();
    void delete(Car car);

    Car findCarById(UUID id);
}
