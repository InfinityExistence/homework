package org.app.controller;

import junit.framework.TestCase;
import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.exception.ForbiddenException;
import org.app.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderControllerImplTest extends TestCase {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderControllerImpl orderController;

    private UserCredentials userCredentials;
    private UserData userData;
    private Car car;
    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userData = UserData.builder()
                .orders(new ArrayList<>())
                .build();
        userCredentials = UserCredentials.builder()
                .data(userData)
                .build();
        userData.setUserCredentials(userCredentials);
        car = new Car();
        order = Order.builder()
                .id(UUID.randomUUID())
                .car(car)
                .client(userData)
                .status("Ordered")
                .build();
        car.setOrder(order);
        userData.getOrders().add(order);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.findAllOrder()).thenReturn(orders);

        List<Order> result = orderController.getAllOrders(userCredentials);

        assertEquals(orders, result);
        verify(orderService, times(1)).findAllOrder();
    }

    @Test
    public void testGetOrderById() {
        UUID orderId = order.getId();
        when(orderService.findOrder(orderId)).thenReturn(order);

        Order result = orderController.getOrderById(orderId, userCredentials);

        assertEquals(order, result);
        verify(orderService, times(1)).findOrder(orderId);
    }
@Test
    public void testEditOrder() {
    when(orderService.saveOrder(order)).thenReturn(order);

    Order result = orderController.editOrder(order, userCredentials);

    assertEquals(order, result);
    verify(orderService, times(1)).saveOrder(order);
    }

    public void testDeleteOrder() {
        orderController.deleteOrder(order, userCredentials);

        verify(orderService, times(1)).deleteOrder(order);
        assertFalse(userData.getOrders().contains(order));
    }

    public void testDeleteMyOrder() {
        orderController.deleteMyOrder(order, userCredentials);

        verify(orderService, times(1)).deleteOrder(order);
        assertFalse(userData.getOrders().contains(order));
    }

    public void testMakeOrder() {
        when(orderService.isExistsByCar(car)).thenReturn(false);
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        Order result = orderController.makeOrder(car, userData, userCredentials);

        assertEquals(order, result);
        verify(orderService, times(1)).isExistsByCar(car);
        verify(orderService, times(1)).saveOrder(any(Order.class));
    }

    public void testMakeOrderCarAlreadyOrdered() {
        when(orderService.isExistsByCar(car)).thenReturn(true);

        assertThrows(ForbiddenException.class, () -> {
            orderController.makeOrder(car, userData, userCredentials);
        });

        verify(orderService, times(1)).isExistsByCar(car);
        verify(orderService, never()).saveOrder(any(Order.class));
    }

    @Test
    public void testMakeMyOrder() {
        when(orderService.isExistsByCar(car)).thenReturn(false);
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        Order result = orderController.makeMyOrder(car, userCredentials);

        assertEquals(order, result);
        verify(orderService, times(1)).isExistsByCar(car);
        verify(orderService, times(1)).saveOrder(any(Order.class));
    }

    @Test
    public void testMakeMyOrderCarAlreadyOrdered() {
        when(orderService.isExistsByCar(car)).thenReturn(true);

        assertThrows(ForbiddenException.class, () -> {
            orderController.makeMyOrder(car, userCredentials);
        });

        verify(orderService, times(1)).isExistsByCar(car);
        verify(orderService, never()).saveOrder(any(Order.class));
    }

    @Test
    public void testGetMyOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.findByUser(userData)).thenReturn(orders);

        List<Order> result = orderController.getMyOrders(userCredentials);

        assertEquals(orders, result);
        verify(orderService, times(1)).findByUser(userData);
    }
}