package org.app.repository;

import org.app.entity.UserCredentials;

import java.util.HashMap;

public class UserMemoryCredentialsRepository implements UserCredentialsRepository {

    HashMap<String ,UserCredentials> credentialsMemory = new HashMap<>(100);

    @Override
    public UserCredentials save(UserCredentials userData) {
        credentialsMemory.put(userData.getLogin(), userData);
        return userData;
    }

    @Override
    public UserCredentials findByLogin(String login) {
        return credentialsMemory.get(login);
    }

    @Override
    public void deleteByLogin(String login) {
        credentialsMemory.remove(login);
    }

    @Override
    public boolean existsByLogin(String login) {
        return credentialsMemory.get(login) != null;
    }
}
