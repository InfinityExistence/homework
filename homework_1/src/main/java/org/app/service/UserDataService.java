package org.app.service;

import org.app.entity.UserData;

import java.util.List;
import java.util.UUID;

public interface UserDataService {
    UserData saveUser(UserData userData);
    UserData findUser(UUID id);
    List<UserData> findAllUsers();
    void deleteUser(UserData userData);
}
