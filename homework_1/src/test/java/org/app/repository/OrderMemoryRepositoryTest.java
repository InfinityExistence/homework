package org.app.repository;

import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMemoryRepositoryTest {

    private OrderMemoryRepository orderRepository;
    private Order order1;
    private Order order2;
    private Car car1;
    private Car car2;
    private UserData userData1;
    private UserData userData2;

    @BeforeEach
    public void setUp() {
        orderRepository = new OrderMemoryRepository();

        car1 = new Car();
        car1.setId(UUID.randomUUID());
        car1.setBrand("LADA");
        car1.setModel("Comedy");

        car2 = new Car();
        car2.setId(UUID.randomUUID());
        car2.setBrand("LADA");
        car2.setModel("Granta");

        userData1 = new UserData();
        userData1.setId(UUID.randomUUID());
        userData1.setName("Саня");

        userData2 = new UserData();
        userData2.setId(UUID.randomUUID());
        userData2.setName("Даня");

        order1 = Order.builder()
                .id(UUID.randomUUID())
                .car(car1)
                .client(userData1)
                .status("Ordered")
                .build();

        order2 = Order.builder()
                .id(UUID.randomUUID())
                .car(car2)
                .client(userData2)
                .status("Ordered")
                .build()
        ;
    }

    @Test
    public void testSave() {
        Order savedOrder = orderRepository.save(order1);

        assertNotNull(savedOrder.getId());
        assertEquals(order1, savedOrder);
        assertTrue(orderRepository.findAll().contains(order1));
    }

    @Test
    public void testFindById() {
        orderRepository.save(order1);

        Order foundOrder = orderRepository.findById(order1.getId());

        assertEquals(order1, foundOrder);
    }

    @Test
    public void testFindByIdNotFound() {
        Order foundOrder = orderRepository.findById(UUID.randomUUID());

        assertNull(foundOrder);
    }

    @Test
    public void testFindAll() {
        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> orders = orderRepository.findAll();

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testDelete() {
        orderRepository.save(order1);
        orderRepository.save(order2);

        orderRepository.delete(order1);

        List<Order> orders = orderRepository.findAll();

        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testFindByUser() {
        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> userOrders = orderRepository.findByUser(userData1);

        assertEquals(1, userOrders.size());
        assertTrue(userOrders.contains(order1));
    }

    @Test
    public void testIsExistsByCar() {
        orderRepository.save(order1);

        boolean exists = orderRepository.isExistsByCar(car1);

        assertTrue(exists);
    }

    @Test
    public void testIsExistsByCarNotFound() {
        orderRepository.save(order1);

        boolean exists = orderRepository.isExistsByCar(car2);

        assertFalse(exists);
    }
}
