package org.app.controller;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.ioc.annotation.HasAnyRole;
import org.app.ioc.annotation.Logging;

import java.util.List;
import java.util.UUID;

public interface OrderController {
    @HasAnyRole(roles = {"ADMIN", "MANAGER"})
    public List<Order> getAllOrders(UserCredentials credentials);
    @HasAnyRole(roles = {"ADMIN", "MANAGER"})
    public Order getOrderById(UUID id, UserCredentials credentials);
    @HasAnyRole(roles = {"ADMIN"})
    @Logging(MethodDescription = "редактирование заказа")
    public Order editOrder(Order order, UserCredentials credentials);
    @HasAnyRole(roles = {"ADMIN"})
    @Logging(MethodDescription = "удаление заказа")
    public void deleteOrder(Order order, UserCredentials credentials);
    @Logging(MethodDescription = "удаление заказа")
    public void deleteMyOrder(Order order, UserCredentials credentials);

    @Logging(MethodDescription = "создание заказа")
    @HasAnyRole(roles = {"ADMIN"})
    public Order makeOrder(Car car, UserData client, UserCredentials credentials);
    @Logging(MethodDescription = "создание заказа")
    public Order makeMyOrder(Car car,UserCredentials credentials);

    public List<Order> getMyOrders(UserCredentials userCredentials);
}
