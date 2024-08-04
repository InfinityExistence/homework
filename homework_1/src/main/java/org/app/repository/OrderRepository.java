package org.app.repository;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserData;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    Order findById(UUID id);
    List<Order> findAll();
    void delete(Order order);

    List<Order> findByUser(UserData data);

    boolean isExistsByCar(Car car);
}
