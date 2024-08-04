package org.app.repository;

import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserMemoryCredentialsRepositoryTest {

    private UserMemoryCredentialsRepository userCredentialsRepository;
    private UserCredentials userCredentials1;
    private UserCredentials userCredentials2;

    @BeforeEach
    public void setUp() {
        userCredentialsRepository = new UserMemoryCredentialsRepository();

        UserData userData1 = UserData.builder()
                .id(UUID.randomUUID())
                .name("Андрей")
                .build();

        UserData userData2 = UserData.builder()
                .id(UUID.randomUUID())
                .name("Борис")
                .build();

        userCredentials1 = UserCredentials.builder()
                .login("andrew")
                .password("password123")
                .data(userData1)
                .build();

        userCredentials2 = UserCredentials.builder()
                .login("boris")
                .password("password456")
                .data(userData2)
                .build();
    }

    @Test
    public void testSave() {
        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials1);

        assertEquals(userCredentials1, savedUserCredentials);
        assertTrue(userCredentialsRepository.existsByLogin(userCredentials1.getLogin()));
    }

    @Test
    public void testFindByLogin() {
        userCredentialsRepository.save(userCredentials1);

        UserCredentials foundUserCredentials = userCredentialsRepository.findByLogin(userCredentials1.getLogin());

        assertEquals(userCredentials1, foundUserCredentials);
    }

    @Test
    public void testFindByLoginNotFound() {
        UserCredentials foundUserCredentials = userCredentialsRepository.findByLogin("non_existent_login");

        assertNull(foundUserCredentials);
    }

    @Test
    public void testDeleteByLogin() {
        userCredentialsRepository.save(userCredentials1);
        userCredentialsRepository.save(userCredentials2);

        userCredentialsRepository.deleteByLogin(userCredentials1.getLogin());

        assertFalse(userCredentialsRepository.existsByLogin(userCredentials1.getLogin()));
        assertTrue(userCredentialsRepository.existsByLogin(userCredentials2.getLogin()));
    }

    @Test
    public void testExistsByLogin() {
        userCredentialsRepository.save(userCredentials1);

        boolean exists = userCredentialsRepository.existsByLogin(userCredentials1.getLogin());

        assertTrue(exists);
    }

    @Test
    public void testExistsByLoginNotFound() {
        boolean exists = userCredentialsRepository.existsByLogin("non_existent_login");

        assertFalse(exists);
    }
}
