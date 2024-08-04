package org.app.service;

import org.app.entity.UserData;
import org.app.ioc.annotation.AutoInjected;
import org.app.repository.UserDataRepository;

import java.util.List;
import java.util.UUID;

public class UserDataServiceImpl implements UserDataService {
    @AutoInjected
    UserDataRepository userDataRepository;
    @Override
    public UserData saveUser(UserData userData) {
        return userDataRepository.save(userData);
    }

    @Override
    public UserData findUser(UUID id) {
        return userDataRepository.findById(id);
    }

    @Override
    public List<UserData> findAllUsers() {
        return userDataRepository.findAll();
    }

    @Override
    public void deleteUser(UserData userData) {
        userDataRepository.delete(userData);
    }
}
