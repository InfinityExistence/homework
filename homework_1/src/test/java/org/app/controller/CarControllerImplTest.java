package org.app.controller;

import org.app.entity.Car;
import org.app.entity.UserCredentials;
import org.app.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarControllerImplTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarControllerImpl carController;

    private Car car;
    private UserCredentials userCredentials;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        car = new Car();
        car.setId(UUID.randomUUID());

        userCredentials = UserCredentials.builder().build();
    }

    @Test
    public void testGetCarByUUID() {
        UUID carId = car.getId();
        when(carService.getCarByUUID(carId)).thenReturn(car);

        Car result = carController.getCarByUUID(carId);

        assertEquals(car, result);
        verify(carService, times(1)).getCarByUUID(carId);
    }

    @Test
    public void testViewCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carService.findAllCars()).thenReturn(cars);

        List<Car> result = carController.viewCars();

        assertEquals(cars, result);
        verify(carService, times(1)).findAllCars();
    }

    @Test
    public void testSearchCars() {
        Car carToSearch = new Car();
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carService.findCar(carToSearch)).thenReturn(cars);

        List<Car> result = carController.searchCars(carToSearch);

        assertEquals(cars, result);
        verify(carService, times(1)).findCar(carToSearch);
    }

    @Test
    public void testAddCar() {
        when(carService.saveCar(car)).thenReturn(car);

        Car result = carController.addCar(car, userCredentials);

        assertEquals(car, result);
        verify(carService, times(1)).saveCar(car);
    }

    @Test
    public void testDeleteCar() {
        carController.deleteCar(car, userCredentials);

        verify(carService, times(1)).deleteCar(car);
    }
}