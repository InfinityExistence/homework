package org.app.repository;

import org.app.controller.CarController;
import org.app.controller.OrderController;
import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.ioc.annotation.AutoInjected;
import org.app.secure.password.PasswordEncoder;
import org.app.service.LogService;

import java.util.ArrayList;
import java.util.List;

public class DataBaseDefaultInfoInitializer {

    @AutoInjected
    CarController carController;
    @AutoInjected
    UserDataRepository userDataRepository;
    @AutoInjected
    OrderRepository orderRepository;
    @AutoInjected
    LogService logService;
    @AutoInjected
    OrderController orderController;
    @AutoInjected
    UserCredentialsRepository userCredentialsRepository;
    @AutoInjected
    PasswordEncoder passwordEncoder;


    public void initAllData() {
        UserCredentials admin = initAdmin();

        Car car = initCars(admin);
        initUserAndOrders(car);
        initManager();

    }

    private UserCredentials initAdmin() {
        UserCredentials credentials = UserCredentials.builder()
                .login("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(List.of("USER", "MANAGER", "ADMIN")).build();
        UserData data = UserData.builder()
                .userCredentials(credentials)
                .name("Марк")
                .phone("+79797979979")
                .email("admin@salon.ru")
                .build();
        credentials.setData(data);
        userDataRepository.save(data);
        userCredentialsRepository.save(credentials);
        return credentials;
    }

    private UserCredentials initManager() {

        UserCredentials credentials = UserCredentials.builder()
                .login("manager")
                .password(passwordEncoder.encode("manager"))
                .roles(List.of("USER", "MANAGER")).build();
        UserData data = UserData.builder()
                .userCredentials(credentials)
                .name("Мулентий")
                .phone("+79788879979")
                .email("manager@salon.ru")
                .build();
        credentials.setData(data);
        userDataRepository.save(data);
        userCredentialsRepository.save(credentials);
        return credentials;
    }

    private UserCredentials initUserAndOrders(Car car) {
        UserCredentials credentials = UserCredentials.builder()
                .login("user")
                .password(passwordEncoder.encode("user"))
                .roles(List.of("USER")).build();
        UserData data = UserData.builder()
                .userCredentials(credentials)
                .name("Валера")
                .phone("+79797970000")
                .email("user@mail.ru")
                .orders(new ArrayList<>())
                .build();
        credentials.setData(data);
        userDataRepository.save(data);
        userCredentialsRepository.save(credentials);
        Order order = orderController.makeMyOrder(car, credentials);

        return credentials;
    }


    private Car initCars(UserCredentials admin) {
        Car car1 = Car.builder()
                .brand("LADA")
                .model("Vesta Comfort")
                .price(1_239_900D)
                .condition("Хорошее")
                .year(2023).build();
        Car car2 = Car.builder()
                .brand("LADA")
                .model("Vesta Comfort'24")
                .price(1_513_900D)
                .condition("Хорошее")
                .year(2024).build();
        Car car3 = Car.builder()
                .brand("LADA")
                .model("Granta Standard")
                .price(699_900D)
                .condition("Плохое")
                .year(2011).build();
        Car car4 = Car.builder()
                .brand("LADA")
                .model("Granta Classic'24")
                .price(911_000D)
                .condition("Хорошее")
                .year(2021).build();
        Car car5 = Car.builder()
                .brand("LADA")
                .model("Granta Comfort'24")
                .price(1_022_000D)
                .condition("Хорошее")
                .year(2022).build();
        carController.addCar(car1, admin);
        carController.addCar(car2, admin);
        carController.addCar(car3, admin);
        carController.addCar(car4, admin);
        carController.addCar(car5, admin);
        return car3;
    }


}
