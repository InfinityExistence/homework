package org.app.repository;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserData;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OrderMemoryRepository implements OrderRepository {
    HashMap<UUID, Order> orderMemory = new HashMap<>(100);
    @Override
    public Order save(Order order) {
        UUID id = order.getId();
        if (id == null) {
            id = UUID.randomUUID();
            order.setId(id);
        }

        orderMemory.put(id, order);
        return order;
    }

    @Override
    public Order findById(UUID id) {
        return orderMemory.get(id);
    }

    @Override
    public List<Order> findAll() {
        return orderMemory.values().stream().toList();
    }


    @Override
    public void delete(Order order) {
        orderMemory.remove(order.getId());
    }

    @Override
    public List<Order> findByUser(UserData data) {
        return orderMemory.values().stream()
                .filter(order -> order.getClient().equals(data))
                .toList();
    }

    @Override
    public boolean isExistsByCar(Car car) {
        return orderMemory.values().stream()
                .anyMatch(order -> order
                        .getCar().getId()
                                .equals(car.getId()));
    }
}
