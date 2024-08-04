package org.app.controller;

import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.ioc.annotation.AutoInjected;
import org.app.service.UserDataService;

import java.util.List;
import java.util.UUID;

public class UserDataControllerImpl implements UserDataController {
    @AutoInjected
    UserDataService userDataService;

    @Override
    public UserData findUser(UUID id, UserCredentials credentials) {
        return userDataService.findUser(id);
    }

    @Override
    public List<UserData> findAllUsers(UserCredentials credentials) {
        return userDataService.findAllUsers();
    }

    @Override
    public void deleteUser(UserData userData, UserCredentials credentials) {
        userDataService.deleteUser(userData);
    }

    @Override
    public UserData saveData(UserData userData) {
        return userDataService.saveUser(userData);
    }
}
