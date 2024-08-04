package org.app.service;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserData;
import org.app.ioc.annotation.AutoInjected;
import org.app.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    @AutoInjected
    OrderRepository orderRepository;
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrder(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public List<Order> findByUser(UserData data) {
        return orderRepository.findByUser(data);
    }

    @Override
    public boolean isExistsByCar(Car car) {
        return orderRepository.isExistsByCar(car);
    }
}
