package org.app.console.view.output;

import org.app.entity.Car;
import org.app.entity.Log;
import org.app.entity.Order;
import org.app.entity.UserData;

import java.util.List;

public class EntityPrinter {
    private final String carFormat = "%-10s %-30s %-11d %-10.2f %-10s %s\n";
    private final String carHeader = "%-10s %-30s %-11s %-10s %-10s %s\n";
    private final String userFormat = "%-10s %-20s %-14s %-15d %-25s %s\n";
    private final String userHeader = "%-10s %-20s %-14s %-15s %-25s %s\n";
    private final String orderFormat = "%-20s %-30s %-11s %-37s %-37s %-37s\n";
    private final String logFormat = "%-33s %-15s %-35s %-37s\n";
    public void printAllCarsWithHeader(List<Car> cars) {
        printCarHeader();
        cars.forEach(this::printCar);
    }

    public void printCarWithHeader(Car car) {
        printCarHeader();
        printCar(car);
    }
    private void printCar(Car car) {
        System.out.printf(carFormat, car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getCondition(), car.getId());
    }
    private void printCarHeader() {
        System.out.printf(carHeader, "Марка", "Модель", "Год выпуска", "Цена", "Состояние", "UUID");
    }
    public void printAllUsersWithHeader(List<UserData> userData) {
        printUserHeader();
        userData.forEach(this::printUser);
    }
    public void printUserWithHeader(UserData userData) {
        printUserHeader();
        printUser(userData);
    }

    private void printUserHeader() {
        System.out.printf(userHeader, "Имя", "Почта","Телефон", "Кол-во заказов","Роли", "UUID");
    }

    private void printUser(UserData userData) {
        System.out.printf(userFormat, userData.getName(), userData.getEmail(),userData.getPhone(), userData.getOrders().size(),userData.getUserCredentials().getRoles(), userData.getId());
    }

    public void printAllOrdersWithHeader(List<Order> orders) {
        printOrderHeader();
        orders.forEach(this::printOrder);
    }

    public void printOrderWithHeader(Order order) {
        printOrderHeader();
        printOrder(order);
    }
    private void printOrderHeader() {
        System.out.printf(orderFormat, "Статус", "Модель автомобиля", "Имя клиента", "UUID Заказа", "UUID Автомобиля", "UUID клиента");
    }

    private void printOrder(Order order) {
        System.out.printf(orderFormat, order.getStatus(), order.getCar().getModel(), order.getClient().getName(), order.getId(), order.getCar().getId(), order.getClient().getId());
    }

    public void printLogsWithHeader(List<Log> logs) {
        printLogsHeader();
        logs.forEach(this::printLog);

    }


    private void printLog(Log log) {
        System.out.printf(logFormat, log.getTime(), log.getUserName(), log.getAction(),log.getUser());

    }

    private void printLogsHeader() {
        System.out.printf(logFormat, "Время", "Пользователь", "Описание действиия", "UUID");
    }
}
