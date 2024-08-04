package org.app.controller;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.exception.ForbiddenException;
import org.app.ioc.annotation.AutoInjected;
import org.app.service.OrderService;

import java.util.List;
import java.util.UUID;

public class OrderControllerImpl implements OrderController {
    @AutoInjected
    private OrderService orderService;

    @Override
    public List<Order> getAllOrders(UserCredentials credentials) {
        return orderService.findAllOrder();

    }

    @Override
    public Order getOrderById(UUID id, UserCredentials credentials) {
        return orderService.findOrder(id);
    }

    @Override
    public Order editOrder(Order order, UserCredentials credentials) {
        return orderService.saveOrder(order);
    }

    @Override
    public void deleteOrder(Order order, UserCredentials credentials) {
        orderService.deleteOrder(order);
        order.getClient().getOrders().remove(order);
    }

    @Override
    public void deleteMyOrder(Order order, UserCredentials credentials) {
        List<Order> userOrders = credentials.getData().getOrders();
        if (userOrders.stream()
                .anyMatch(userOrder -> order.getId().equals(userOrder.getId()))) {
            orderService.deleteOrder(order);
            userOrders.remove(order);
        }
    }

    @Override
    public Order makeOrder(Car car, UserData client, UserCredentials credentials) {
        return makeMyOrder(car, client.getUserCredentials());


    }

    @Override
    public Order makeMyOrder(Car car, UserCredentials credentials) {
        if (orderService.isExistsByCar(car))
            throw new ForbiddenException("Данная машина уже заказана.");
        Order order = Order.builder()
                .car(car)
                .client(credentials.getData())
                .status("Ordered")
                .build();
        car.setOrder(order);
        credentials.getData().getOrders().add(order);
        return orderService.saveOrder(order);
    }

    @Override
    public List<Order> getMyOrders(UserCredentials userCredentials) {
        return orderService.findByUser(userCredentials.getData());
    }
}
