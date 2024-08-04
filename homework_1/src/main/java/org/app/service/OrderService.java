package org.app.service;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserData;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> findAllOrder();
    Order findOrder(UUID id);
    void deleteOrder(Order order);

    List<Order> findByUser(UserData data);

    boolean isExistsByCar(Car car);
}
