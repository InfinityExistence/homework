package org.app.repository;

import org.app.entity.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserDataMemoryRepository implements UserDataRepository {
    HashMap<UUID, UserData> userDataMemory = new HashMap<>(100);


    @Override
    public UserData save(UserData userData) {
        if (userData.getId() == null)
            userData.setId(UUID.randomUUID());
        if (userData.getOrders() == null)
            userData.setOrders(new ArrayList<>(5));
        userDataMemory.put(userData.getId(), userData);
        return userData;
    }

    @Override
    public UserData findById(UUID id) {
        return userDataMemory.get(id);
    }

    @Override
    public List<UserData> findAll() {
        return userDataMemory.values().stream().toList();
    }

    @Override
    public void delete(UserData userData) {
        userDataMemory.remove(userData.getId());
    }
}
