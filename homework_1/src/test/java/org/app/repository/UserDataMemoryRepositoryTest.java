package org.app.repository;

import org.app.entity.Order;
import org.app.entity.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataMemoryRepositoryTest {

    private UserDataMemoryRepository userDataRepository;
    private UserData userData1;
    private UserData userData2;

    @BeforeEach
    public void setUp() {
        userDataRepository = new UserDataMemoryRepository();

        userData1 = new UserData();
        userData1.setName("John Doe");

        userData2 = new UserData();
        userData2.setName("Jane Smith");
    }

    @Test
    public void testSave() {
        UserData savedUserData = userDataRepository.save(userData1);

        assertNotNull(savedUserData.getId());
        assertEquals(userData1, savedUserData);
        assertTrue(userDataRepository.findAll().contains(userData1));
    }

    @Test
    public void testSaveWithOrders() {
        Order order1 = Order.builder()
                .id(UUID.randomUUID())
                .status("Ordered")
                .build();
        userData1.setOrders(List.of(order1));

        UserData savedUserData = userDataRepository.save(userData1);

        assertNotNull(savedUserData.getId());
        assertEquals(userData1, savedUserData);
        assertEquals(1, savedUserData.getOrders().size());
        assertTrue(userDataRepository.findAll().contains(userData1));
    }

    @Test
    public void testFindById() {
        userDataRepository.save(userData1);

        UserData foundUserData = userDataRepository.findById(userData1.getId());

        assertEquals(userData1, foundUserData);
    }

    @Test
    public void testFindByIdNotFound() {
        UserData foundUserData = userDataRepository.findById(UUID.randomUUID());

        assertNull(foundUserData);
    }

    @Test
    public void testFindAll() {
        userDataRepository.save(userData1);
        userDataRepository.save(userData2);

        List<UserData> userDataList = userDataRepository.findAll();

        assertEquals(2, userDataList.size());
        assertTrue(userDataList.contains(userData1));
        assertTrue(userDataList.contains(userData2));
    }

    @Test
    public void testDelete() {
        userDataRepository.save(userData1);
        userDataRepository.save(userData2);

        userDataRepository.delete(userData1);

        List<UserData> userDataList = userDataRepository.findAll();

        assertEquals(1, userDataList.size());
        assertTrue(userDataList.contains(userData2));
    }
}
