package org.app.controller;

import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.service.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDataControllerImplTest {

    @Mock
    private UserDataService userDataService;

    @InjectMocks
    private UserDataControllerImpl userDataController;

    private UserData userData;
    private UserCredentials userCredentials;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userData = new UserData();
        userData.setId(UUID.randomUUID());
        userCredentials = UserCredentials.builder().build();

    }

    @Test
    public void testFindUser() {
        UUID userId = userData.getId();
        when(userDataService.findUser(userId)).thenReturn(userData);

        UserData result = userDataController.findUser(userId, userCredentials);

        assertEquals(userData, result);
        verify(userDataService, times(1)).findUser(userId);
    }

    @Test
    public void testFindAllUsers() {
        List<UserData> users = new ArrayList<>();
        users.add(userData);
        when(userDataService.findAllUsers()).thenReturn(users);

        List<UserData> result = userDataController.findAllUsers(userCredentials);

        assertEquals(users, result);
        verify(userDataService, times(1)).findAllUsers();
    }

    @Test
    public void testDeleteUser() {
        userDataController.deleteUser(userData, userCredentials);

        verify(userDataService, times(1)).deleteUser(userData);
    }

    @Test
    public void testSaveData() {
        when(userDataService.saveUser(userData)).thenReturn(userData);

        UserData result = userDataController.saveData(userData);

        assertEquals(userData, result);
        verify(userDataService, times(1)).saveUser(userData);
    }
}