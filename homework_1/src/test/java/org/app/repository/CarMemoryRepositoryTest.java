package org.app.repository;

import org.app.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CarMemoryRepositoryTest {

    private CarMemoryRepository carRepository;
    private Car car1;
    private Car car2;

    @BeforeEach
    public void setUp() {
        carRepository = new CarMemoryRepository();

        car1 = new Car();
        car1.setId(UUID.randomUUID());
        car1.setBrand("LADA");
        car1.setModel("BEBRA");
        car1.setCondition("New");
        car1.setYear(2022);
        car1.setPrice(25000.0);

        car2 = new Car();
        car2.setId(UUID.randomUUID());
        car2.setBrand("LADA");
        car2.setModel("Comfort");
        car2.setCondition("Used");
        car2.setYear(2020);
        car2.setPrice(18000.0);
    }

    @Test
    public void testSave() {
        Car savedCar = carRepository.save(car1);

        assertNotNull(savedCar.getId());
        assertEquals(car1, savedCar);
        assertTrue(carRepository.findAll().contains(car1));
    }

    @Test
    public void testFindAll() {
        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> cars = carRepository.findAll();

        assertEquals(2, cars.size());
        assertTrue(cars.contains(car1));
        assertTrue(cars.contains(car2));
    }

    @Test
    public void testFind() {
        carRepository.save(car1);
        carRepository.save(car2);

        Car searchCar = new Car();
        searchCar.setBrand("LADA");
        searchCar.setModel("BEBRA");

        List<Car> foundCars = carRepository.find(searchCar);

        assertEquals(1, foundCars.size());
        assertTrue(foundCars.contains(car1));
    }

    @Test
    public void testFindById() {
        carRepository.save(car1);

        Car foundCar = carRepository.findCarById(car1.getId());

        assertEquals(car1, foundCar);
    }

    @Test
    public void testFindByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> {
            carRepository.findCarById(UUID.randomUUID());
        });
    }

    @Test
    public void testDelete() {
        carRepository.save(car1);
        carRepository.save(car2);

        carRepository.delete(car1);

        List<Car> cars = carRepository.findAll();

        assertEquals(1, cars.size());
        assertTrue(cars.contains(car2));
    }

    @Test
    public void testIsCarLikeExpected() {
        carRepository.save(car1);
        carRepository.save(car2);

        Car searchCar = new Car();
        searchCar.setBrand("LADA");
        searchCar.setModel("BEBRA");
        searchCar.setCondition("New");
        searchCar.setYear(2022);
        searchCar.setPrice(25000.0);

        List<Car> foundCars = carRepository.find(searchCar);

        assertEquals(1, foundCars.size());
        assertTrue(foundCars.contains(car1));
    }

    @Test
    public void testIsCarLikeExpectedWithPriceRange() {
        carRepository.save(car1);
        carRepository.save(car2);

        Car searchCar = new Car();
        searchCar.setPrice(24500.0);

        List<Car> foundCars = carRepository.find(searchCar);

        assertEquals(1, foundCars.size());
        assertTrue(foundCars.contains(car1));
    }
}
