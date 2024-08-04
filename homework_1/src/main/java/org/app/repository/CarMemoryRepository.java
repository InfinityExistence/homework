package org.app.repository;

import org.app.entity.Car;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.Math.abs;

public class CarMemoryRepository implements CarRepository {
    HashSet<Car> carMemory = new HashSet<>(100);
    @Override
    public Car save(Car car) {
        UUID id = car.getId();
        if (id == null) {
            id = UUID.randomUUID();
            car.setId(id);
        }

        carMemory.add(car);
        return car;
    }

    @Override
    public List<Car> find(Car car) {
        if (car == null)
            return carMemory.stream().toList();
        return carMemory.stream().filter(item -> isCarLikeExpected(item, car)).toList();
    }

    @Override
    public List<Car> findAll() {
        return carMemory.stream().toList();
    }

    private boolean isCarLikeExpected(Car carFromDb, Car expectedCar) {
        boolean result = true;
        String expectedBrand = expectedCar.getBrand();
        String expectedModel = expectedCar.getModel();
        String expectedCondition = expectedCar.getCondition();
        Integer expectedYear = expectedCar.getYear();
        Double expectedPrice = expectedCar.getPrice();

        if (expectedBrand != null)
            result &= carFromDb.getBrand().toLowerCase().contains(expectedBrand.toLowerCase());
        if (expectedModel != null)
            result &= carFromDb.getModel().toLowerCase().contains(expectedModel.toLowerCase());
        if (expectedCondition != null)
            result &= carFromDb.getCondition().toLowerCase().contains(expectedCondition.toLowerCase());
        if (expectedYear != null)
            result &= carFromDb.getYear().equals(expectedYear);
        if (expectedPrice != null)
            result &= abs(expectedPrice - carFromDb.getPrice()) < 1000;
        return result;
    }

    @Override
    public void delete(Car car) {
        carMemory.remove(car);
    }

    @Override
    public Car findCarById(UUID id) {
        return carMemory.stream().filter(car -> car.getId().equals(id)).findFirst().orElseThrow(() -> new NoSuchElementException("Машина не найдена"));
    }
}
